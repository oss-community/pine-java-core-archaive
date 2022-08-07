# wsl sudo sysctl -w vm.max_map_count=262144

docker --version
docker-compose --version
docker-machine --version

echo GITHUB_JENKINS_TOKEN=%GITHUB_JENKINS_TOKEN%> .\pipeline\.env

docker-compose -f .\pipeline\docker-compose-test.yaml ^
-p pine-jenkins-test ^
--env-file .\pipeline\.env ^
up --build