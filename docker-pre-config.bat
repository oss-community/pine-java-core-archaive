docker --version
docker-compose --version
docker-machine --version

mkdir %HOMEPATH%\docker_compose\jenkins ^
%HOMEPATH%\docker_compose\jfrog-postgresql ^
%HOMEPATH%\docker_compose\jfrog ^
%HOMEPATH%\docker_compose\nexus\nexus-data ^
%HOMEPATH%\docker_compose\concourse-postgresql ^
%HOMEPATH%\docker_compose\concourse\keys ^
%HOMEPATH%\docker_compose\sonarqube-postgresql ^
%HOMEPATH%\docker_compose\sonarqube\sonarqube_data ^
%HOMEPATH%\docker_compose\sonarqube\sonarqube_extensions ^
%HOMEPATH%\docker_compose\sonarqube\sonarqube_logs 2>null

wsl sudo sysctl -w vm.max_map_count=262144