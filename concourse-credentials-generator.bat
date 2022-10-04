echo # credentials > .\ci\concourse\credentials.yml
echo github-user-name: %GITHUB_USERNAME% >> .\ci\concourse\credentials.yml
echo github-user-email: %GITHUB_EMAIL% >> .\ci\concourse\credentials.yml

echo github-package-token: %GITHUB_PACKAGE_TOKEN% >> .\ci\concourse\credentials.yml
echo github-artifactory-urL: %GITHUB_ARTIFACTORY_URL% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-username: %JFROG_ARTIFACTORY_USERNAME% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-password: %JFROG_ARTIFACTORY_PASSWORD% >> .\ci\concourse\credentials.yml
echo sonar-token: %SONAR_TOKEN% >> .\ci\concourse\credentials.yml

echo github-key-pub: >> .\ci\concourse\credentials.yml
echo github-key: ^|- >> .\ci\concourse\credentials.yml
mkdir .\docker\ssh
call ssh-keygen -t rsa -C "concourse_team" -f .\docker\ssh\id_rsa
call ssh-keygen -t rsa -b 4096 -m PEM -f .\docker\ssh\session_signing_key
call ssh-keygen -t rsa -b 4096 -m PEM -f .\docker\ssh\tsa_host_key
call ssh-keygen -t rsa -b 4096 -m PEM -f .\docker\ssh\worker_key
REM del .\ssh\id_rsa
REM del .\ssh\id_rsa.pub
REM rmdir .\ssh
