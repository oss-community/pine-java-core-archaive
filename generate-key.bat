mkdir .\ssh
call ssh-keygen -t rsa -C "concourse_team" -N '' -f .\ssh\id_rsa
echo %GITHUB_JENKINS_TOKEN% > .\github-token.txt
call gh auth login -p ssh -h github.com --with-token < .\github-token.txt
REM gh repo deploy-key delete $(gh repo deploy-key list -R saman-oss/pine-java-core
call gh repo deploy-key add .\ssh\id_rsa.pub -R saman-oss/pine-java-core -t concourse-key-pub -w

echo # credentials > .\ci\concourse\credentials.yml
FOR /F "tokens=* delims=" %%x in (.\ssh\id_rsa.pub) DO echo git-key-pub--pine-java-core: %%x >> .\ci\concourse\credentials.yml
echo git-key-pine-java-core: ^| >> .\ci\concourse\credentials.yml
FOR /F "tokens=* delims=" %%x in (.\ssh\id_rsa) DO echo    %%x >> .\ci\concourse\credentials.yml
del .\github-token.txt
del .\ssh\id_rsa
del .\ssh\id_rsa.pub
rmdir .\ssh
