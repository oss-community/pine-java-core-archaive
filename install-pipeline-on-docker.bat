call cls
call .\pipeline\dockerfiles\pre-config.bat
call .\pipeline\dockerfiles\create-env.bat

docker-compose -f .\pipeline\dockerfiles\docker-compose.yaml ^
-p pine-pipeline ^
--env-file .\pipeline\dockerfiles\.env ^
up --build -d