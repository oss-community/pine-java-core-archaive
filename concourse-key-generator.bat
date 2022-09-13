echo # credentials > .\ci\concourse\credentials.yml
echo github-user-name: %GITHUB_USERNAME% >> .\ci\concourse\credentials.yml
echo github-user-email: %GITHUB_EMAIL% >> .\ci\concourse\credentials.yml
echo github-key-pub: >> .\ci\concourse\credentials.yml
echo github-key: ^|- >> .\ci\concourse\credentials.yml
mkdir .\ssh
call ssh-keygen -t rsa -C "concourse_team" -f .\ssh\id_rsa
REM del .\ssh\id_rsa
REM del .\ssh\id_rsa.pub
REM rmdir .\ssh
