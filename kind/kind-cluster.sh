#! /usr/bin/env bash


set -o pipefail
set -o errexit
set -o nounset

readonly KIND=${KIND:-kind}
readonly KUBECTL=${KUBECTL:-kubectl}

readonly CTRBUILD=${CTRBUILD:-docker}

readonly CLUSTERNAME=${CLUSTERNAME:-learnk8s}
readonly WAITTIME=${WAITTIME:-5m}

readonly HERE=$(cd "$(dirname "$0")" && pwd)
readonly REPO=$(cd "${HERE}/.." && pwd)

readonly KIND_IMAGE="kindest/node:v1.27.3-learnk8s"

readonly REG_NAME="kind-registry"
readonly REG_PORT="5001"

readonly REGISTRY_DIR="/etc/containerd/certs.d/localhost:${REG_PORT}"



kind::cluster::exists() {
    ${KIND} get clusters | grep -q "$1"
}


kind::registry::start() {
    if [ "$(docker inspect -f '{{.State.Running}}' "${REG_NAME}" 2>/dev/null || true)" != 'true' ]; then
        if [ "$(docker inspect -f '{{.State.Running}}' "${REG_NAME}" 2>/dev/null || true)" == 'false' ]; then
            docker rm ${REG_NAME}
        fi
        ## attempt to remove kind-registry
        docker run -d --restart=always -p "127.0.0.1:${REG_PORT}:5000" --network bridge --name "${REG_NAME}"  registry:2
    fi
}

kind::cluster::create() {
    kind::registry::start

    ${KIND} create cluster \
        --name "${CLUSTERNAME}" \
        --wait "${WAITTIME}" \
        --config "${REPO}/kind/kind-config.yaml" \
        --image "${KIND_IMAGE}" 

    kind::cluster::config
}

kind::cluster::config() {

    for node in $(kind get nodes -n ${CLUSTERNAME}); do
        docker exec "${node}" mkdir -p "${REGISTRY_DIR}"
cat <<EOF | docker exec -i "${node}" cp /dev/stdin "${REGISTRY_DIR}/hosts.toml"
[host."http://${REG_NAME}:5000"]
EOF
done


if [ "$(docker inspect -f='{{json .NetworkSettings.Networks.kind}}' "${REG_NAME}")" = 'null' ]; then
  docker network connect "kind" "${REG_NAME}"
fi
}


kind::cluster::delete() {
    ${KIND}  delete  clusters ${CLUSTERNAME} 
}

kind::cluster::load() {
    ${KIND} load docker-image \
        --name "${CLUSTERNAME}" \
        "$@"
}


kind::command::up(){

    if kind::cluster::exists "$CLUSTERNAME" ; then
        echo "cluster $CLUSTERNAME already exists"
        echo exit 2
    fi

    # Create a fresh kind cluster.
    if ! kind::cluster::exists "$CLUSTERNAME" ; then
        kind::cluster::create
    fi

}


kind::command::down(){

    if kind::cluster::exists "$CLUSTERNAME" ; then
        echo "cluster $CLUSTERNAME  exists, removing cluster.... "
        kind::cluster::delete
    fi

}

kind::command::build(){

    ${CTRBUILD} build -t $KIND_IMAGE -f "${REPO}/kind/Dockerfile" .
    # docker build -t $KIND_IMAGE -f "${REPO}/kind/Dockerfile" .
}

kind::command::config(){
    kind::cluster::config
}

kind::command::$1

