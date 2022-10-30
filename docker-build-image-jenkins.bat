docker build -t %DOCKER_USERNAME%/jenkins:latest .\docker\jenkins\ --no-cache
docker push %DOCKER_USERNAME%/jenkins:latest