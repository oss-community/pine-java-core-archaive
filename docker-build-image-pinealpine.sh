docker build -t $DOCKER_USERNAME/pine-alpine:latest ./docker/pinealpine/ --no-cache
docker push $DOCKER_USERNAME/pine-alpine:latest