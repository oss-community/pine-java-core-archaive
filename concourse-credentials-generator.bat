REM echo # credentials > .\ci\concourse\credentials.yml
REM echo github-user-name: %GITHUB_USERNAME% >> .\ci\concourse\credentials.yml
REM echo github-user-email: %GITHUB_EMAIL% >> .\ci\concourse\credentials.yml

echo github-package-token: %GITHUB_PACKAGE_TOKEN% >> .\ci\concourse\credentials.yml
echo github-artifactory-urL: %GITHUB_ARTIFACTORY_URL% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-username: %JFROG_ARTIFACTORY_USERNAME% >> .\ci\concourse\credentials.yml
echo jfrog-artifactory-password: %JFROG_ARTIFACTORY_PASSWORD% >> .\ci\concourse\credentials.yml
echo sonar-token: %SONAR_TOKEN% >> .\ci\concourse\credentials.yml

REM echo github-key-pub: >> .\ci\concourse\credentials.yml
REM echo github-key: ^|- >> .\ci\concourse\credentials.yml
REM mkdir .\ssh
REM call ssh-keygen -t rsa -C "concourse_team" -f .\ssh\id_rsa
REM del .\ssh\id_rsa
REM del .\ssh\id_rsa.pub
REM rmdir .\ssh
