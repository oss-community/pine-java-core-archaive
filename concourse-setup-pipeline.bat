fly --target pine set-pipeline ^
--pipeline pine ^
--config .\ci\concourse\pipeline.yml ^
--load-vars-from .\ci\concourse\credentials.yml