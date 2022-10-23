docker --version
docker-compose --version
docker-machine --version

mkdir ~/docker_compose/jenkins \
~/docker_compose/jfrog-postgresql \
~/docker_compose/jfrog \
~/docker_compose/nexus/nexus-data \
~/docker_compose/concourse-postgresql \
~/docker_compose/concourse/keys \
~/docker_compose/sonarqube-postgresql \
~/docker_compose/sonarqube/sonarqube_data \
~/docker_compose/sonarqube/sonarqube_extensions \
~/docker_compose/sonarqube/sonarqube_logs || true

sudo sysctl -w vm.max_map_count=262144