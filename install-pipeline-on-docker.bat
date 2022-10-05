call cls
call .\docker\pre-config.bat
call .\docker\create-env.bat
# call ssh-keygen -t rsa -b 4096 -m PEM -f .\docker\ssh\session_signing_key
# call ssh-keygen -t rsa -b 4096 -m PEM -f .\docker\ssh\tsa_host_key
# call ssh-keygen -t rsa -b 4096 -m PEM -f .\docker\ssh\worker_key
# ren .\docker\ssh\worker_key.pub .\docker\ssh\authorized_worker_keys
# copy .\docker\ssh\* ~\docker_compose\concourse\keys

docker-compose -f .\docker\docker-compose.yaml ^
-p pine-pipeline ^
--env-file .\docker\.env ^
up --build -d