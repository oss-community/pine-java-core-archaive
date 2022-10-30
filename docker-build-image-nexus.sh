docker build -t $DOCKER_USERNAME/nexus:latest ./docker/nexus/ --no-cache
docker push $DOCKER_USERNAME/nexus:latest