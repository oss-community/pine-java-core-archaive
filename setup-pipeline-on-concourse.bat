fly -t pine set-pipeline ^
--pipeline pine ^
--config .\ci\concourse\pipeline.yml ^
--load-vars-from .\ci\concourse\credentials.yml