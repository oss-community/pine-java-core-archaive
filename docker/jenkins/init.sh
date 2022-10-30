git config --global user.email $GITHUB_EMAIL
git config --global user.name $GITHUB_USER
ssh-keygen -t rsa -C "generated in jenkins host" -N '' -f ${HOME}/.ssh/id_rsa
eval "$(ssh-agent -s)" && ssh-add ${HOME}/.ssh/id_rsa
echo "Host *" >> ${HOME}/.ssh/config
echo "    StrictHostKeyChecking no" >> ${HOME}/.ssh/config
echo $$GITHUB_JENKINS_TOKEN > ${HOME}/github-token
gh auth login -p ssh -h github.com --with-token < ${HOME}/github-token
gh repo deploy-key add ${HOME}/.ssh/id_rsa.pub -R $GITHUB_ARTIFACTORY_URL -t jenkins-key-pub -w
# ssh -T git@github.com

java -jar $JENKINS_HOME/jenkins.war --httpPort=$JENKINS_PORT -Xmx2048m