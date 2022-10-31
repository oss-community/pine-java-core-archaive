docker build -t %DOCKER_USERNAME%/openjdk17:latest .\docker\openjdk17\ --no-cache
docker push %DOCKER_USERNAME%/openjdk17:latest