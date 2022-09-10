FROM jenkins/jenkins:jdk17
MAINTAINER samanalishiri@gmail.com

ARG GITHUB_JENKINS_TOKEN=token
ARG KEY_COMMENT=samanalishiri@gmail.com

USER root
RUN apt-get update
RUN apt-get -y install apt-transport-https ca-certificates curl gnupg2 software-properties-common git maven
RUN apt-get update
RUN curl -fsSL https://cli.github.com/packages/githubcli-archive-keyring.gpg | dd of=/usr/share/keyrings/githubcli-archive-keyring.gpg
RUN echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/githubcli-archive-keyring.gpg] https://cli.github.com/packages stable main" | tee /etc/apt/sources.list.d/github-cli.list > /dev/null
RUN apt-get update
RUN apt-get -y install gh
RUN apt-get update
RUN git config --global user.email samanalishiri@gmail.com
RUN git config --global user.name samanalishiri
RUN ssh-keygen -t rsa -C $KEY_COMMENT -N '' -f /root/.ssh/id_rsa
RUN eval "$(ssh-agent -s)" && ssh-add /root/.ssh/id_rsa
RUN echo $GITHUB_JENKINS_TOKEN > ./github-token.txt
RUN gh auth login -p ssh -h github.com --with-token < ./github-token.txt
RUN gh repo deploy-key delete $(gh repo deploy-key list -R saman-oss/pine-java-core | awk '{print ($1==""?"":$1)}') -R saman-oss/pine-java-core || true
RUN gh repo deploy-key add /root/.ssh/id_rsa.pub -R saman-oss/pine-java-core -t jenkins-key-pub -w
#RUN ssh -T git@github.com
RUN java -version
RUN mvn -version
RUN git --version
RUN gh --version

EXPOSE 8080 50000
VOLUME /var/jenkins_home