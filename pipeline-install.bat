call cls
call .\docker-pre-config.bat
call .\docker-create-env.bat
call .\docker-build-image-openjdk17.bat
call .\concourse-generate-credentials.bat

docker-compose -f .\docker\docker-compose.yaml ^
-p pine-pipeline ^
--env-file .\docker\.env ^
up --build -d