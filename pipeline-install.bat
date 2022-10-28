call cls
call .\docker-pre-config.bat
call .\docker-create-env.bat
call .\docker-build-image-openjdk17.bat
call .\docker-build-image-jenkins.bat
call .\docker-build-image-nexus.bat
call .\concourse-generate-credentials.bat

docker compose --file .\docker\docker-compose.yml --project-name pine-pipeline --env-file .\docker\.env up pine-concourse-db pine-concourse-worker --build -d
