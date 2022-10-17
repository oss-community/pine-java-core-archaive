call cls
call .\docker-pre-config.bat
call .\docker-create-env.bat
call .\docker-build-image-openjdk17.bat
call .\concourse-generate-credentials.bat

docker-compose --file .\docker\docker-compose.yaml ^
--project-name pine-pipeline ^
--env-file .\docker\.env ^
up --build -d