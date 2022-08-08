call .\pipeline\docker\pre-config.bat
call .\pipeline\docker\setenv.bat
call .\pipeline\docker\createenv.bat

docker-compose -f .\pipeline\docker\docker-compose.yaml ^
-p pine-pipeline ^
--env-file .\pipeline\docker\.env ^
up --build