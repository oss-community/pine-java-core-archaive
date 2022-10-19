./docker-pre-config
./docker-create-env
./docker-build-image-openjdk17

docker-compose --file ./docker/docker-compose.yml ^
--project-name pine-pipeline ^
--env-file ./docker/.env ^
up --build -d