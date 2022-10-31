#!/bin/bash

git config --global user.email $GITHUB_EMAIL
git config --global user.name $GITHUB_USER
ssh-keygen -t rsa -C "generated in jenkins host" -N '' -f $HOME/.ssh/id_rsa
eval "$(ssh-agent -s)" && ssh-add $HOME/.ssh/id_rsa
echo "Host *" >> $HOME/.ssh/config
echo "    StrictHostKeyChecking no" >> $HOME/.ssh/config

if [ $GITHUB_SECRET != "empty" ]; then
  echo $GITHUB_SECRET > $HOME/.githubtoken
  gh auth login -p ssh -h github.com --with-token < $HOME/.githubtoken
  gh repo deploy-key add $HOME/.ssh/id_rsa.pub -R $GITHUB_URL -t jenkins-key-pub -w
fi

java -jar $APP_HOME/jenkins.war --httpPort=$APP_PORT -Xmx2048m