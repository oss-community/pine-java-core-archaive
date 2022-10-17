echo # credentials > .\ci\concourse\credentials.yml

echo github-user-name: %GITHUB_USERNAME% >> .\ci\concourse\credentials.yml
echo github-user-email: %GITHUB_EMAIL% >> .\ci\concourse\credentials.yml
echo github-package-token: %GITHUB_PACKAGE_TOKEN% >> .\ci\concourse\credentials.yml
echo github-artifactory-url: %GITHUB_ARTIFACTORY_URL% >> .\ci\concourse\credentials.yml

echo jfrog-artifactory-username: %JFROG_ARTIFACTORY_USERNAME% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-encrypted-password: %JFROG_ARTIFACTORY_ENCRYPTED_PASSWORD% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-snapshot-ur: %JFROG_ARTIFACTORY_SNAPSHOT_UR% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-context-url: %JFROG_ARTIFACTORY_CONTEXT_URL% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-repository-prefix: %JFROG_ARTIFACTORY_REPOSITORY_PREFIX% >> .\ci\concourse\credentials.yml

echo sonar-token: %SONAR_TOKEN% >> .\ci\concourse\credentials.yml
echo sonar-url: %SONAR_URL% >> .\ci\concourse\credentials.yml

echo github-key-pub: >> .\ci\concourse\credentials.yml
echo github-key: ^|- >> .\ci\concourse\credentials.yml
mkdir %HOMEPATH%\pine\keys
call ssh-keygen -t rsa -C "concourse_team" -f %HOMEPATH%\pine\keys\id_rsa

call ssh-keygen -t rsa -b 4096 -m PEM -f %HOMEPATH%\pine\keys\session_signing_key
call ssh-keygen -t rsa -b 4096 -m PEM -f %HOMEPATH%\pine\keys\tsa_host_key
call ssh-keygen -t rsa -b 4096 -m PEM -f %HOMEPATH%\pine\keys\worker_key

ren %HOMEPATH%\pine\keys\worker_key.pub %HOMEPATH%\pine\keys\authorized_worker_keys
copy %HOMEPATH%\pine\keys\* %HOMEPATH%\docker_compose\concourse\keys
