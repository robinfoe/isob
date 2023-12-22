#! /usr/bin/env bash

# Copyright Project Contour Authors
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License.  You may obtain
# a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
# License for the specific language governing permissions and limitations
# under the License.

# install-contour-release.sh: Install a specific release of Contour.

set -o pipefail
set -o errexit
set -o nounset

readonly KUBECTL=${KUBECTL:-kubectl}
readonly WAITTIME=${WAITTIME:-5m}

readonly PROGNAME=$(basename "$0")
readonly VERS=${1:-}

readonly HERE=$(cd $(dirname $0) && pwd)
readonly SOURCE_DIR=${HERE}/deploy

printf "Deploying NGINX version 1.9.4 with TCP Services at 32500"
# Install the NGINX local.
${KUBECTL} apply -f $SOURCE_DIR/deploy.yaml
${KUBECTL} wait --timeout="${WAITTIME}" -n ingress-nginx -l app.kubernetes.io/instance=ingress-nginx deployments --for=condition=Available