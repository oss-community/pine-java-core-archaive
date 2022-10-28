./docker-pre-config
./docker-create-env
./docker-build-image-openjdk17
./docker-build-image-jenkins.bat
./docker-build-image-nexus.bat
./concourse-generate-credentials.bat

docker-compose --file ./docker/docker-compose.yml --project-name pine-pipeline --env-file ./docker/.env up --build -d