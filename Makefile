.DEFAULT_GOAL := help
.PHONY: build


VER_CONTOUR=release-1.26
VER_CERT_MANAGER=v1.2.0
CTRBUILD=podman
# VER_OPA=release-3.3



#help: @ List available tasks on this project
help:
	@echo "tasks:"
	@grep -E '[a-zA-Z\.\-]+:.*?@ .*$$' $(MAKEFILE_LIST)| tr -d '#' | grep -v '{developer}' | awk 'BEGIN {FS = ":.*?@ "}; {printf "\t\033[32m%-30s\033[0m %s\n", $$1, $$2}'


#cluster.build: @ build custom kind image
cluster.build: 
	./kind/kind-cluster.sh build


#cluster.up: @ start kind cluster with 1 master and 1 worker
cluster.up: 
	./kind/kind-cluster.sh up


#cluster.down: @ start kind cluster with 1 master and 1 worker
cluster.down: 
	./kind/kind-cluster.sh down

#cluster.config: @ configure privage registry
cluster.config: 
	./kind/kind-cluster.sh config

#cluster.deploy.nginx: @ deploy nginx with TCP service capability at 32500 
cluster.deploy.nginx: 
	./kind/asset/nginx/install-nginx.sh
