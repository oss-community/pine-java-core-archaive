call cls
call .\pipeline\docker\pre-config.bat
call .\pipeline\docker\create-env.bat

docker-compose -f .\pipeline\docker\docker-compose.yaml ^
-p pine-pipeline ^
--env-file .\pipeline\docker\.env ^
up --build