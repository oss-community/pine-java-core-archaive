docker --version
docker-compose --version
docker-machine --version

mkdir ^
~/docker_compose/jenkins_home ^
~/docker_compose/jfrog-postgresql ^
~/docker_compose/concourse-postgresql-1 ^
~/docker_compose/concourse-postgresql-2 ^
~/docker_compose/concourse/keys ^
~/docker_compose/sonarqube-postgresql ^
~/docker_compose/sonarqube/sonarqube_data ^
~/docker_compose/sonarqube/sonarqube_extensions ^
~/docker_compose/sonarqube/sonarqube_logs ^
 2>null

wsl sudo sysctl -w vm.max_map_count=262144