FROM alpine:latest
MAINTAINER samanalishiri@gmail.com

ARG GITHUB_JENKINS_TOKEN=token
ARG GITHUB_ARTIFACTORY_URL=saman-oss/pine-java-core
ARG GITHUB_KEY_COMMENT=samanalishiri@gmail.com

ARG JENKINS_VERSION=latest

USER root

RUN apk add ca-certificates wget curl openrc openssh openjdk17 git maven github-cli
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-2.25-r0.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-bin-2.25-r0.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-i18n-2.25-r0.apk
RUN apk add glibc-bin-2.25-r0.apk glibc-i18n-2.25-r0.apk glibc-2.25-r0.apk

RUN apk add ttf-dejavu

RUN echo "en_US" > ~/locale.md
RUN echo "fa_IR" >> ~/locale.md
RUN cat ~/locale.md | xargs -i /usr/glibc-compat/bin/localedef -i {} -f UTF-8 {}.UTF-8

RUN git config --global user.email samanalishiri@gmail.com
RUN git config --global user.name samanalishiri

RUN ssh-keygen -t rsa -C $GITHUB_KEY_COMMENT -N '' -f ~/.ssh/id_rsa
RUN eval "$(ssh-agent -s)" && ssh-add ~/.ssh/id_rsa
RUN echo "Host *" > ~/.ssh/config
RUN echo "    StrictHostKeyChecking no" >> ~/.ssh/config

RUN echo $GITHUB_JENKINS_TOKEN > ~/github-token.txt
RUN gh auth login -p ssh -h github.com --with-token < ~/github-token.txt
RUN gh repo deploy-key add ~/.ssh/id_rsa.pub -R $GITHUB_ARTIFACTORY_URL -t jenkins-key-pub -w
# RUN ssh -T git@github.com

RUN wget -O jenkins.war https://get.jenkins.io/war/$JENKINS_VERSION/jenkins.war

ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US.UTF-8
# /usr/lib/jvm/java-17-openjdk
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
# /usr/share/java/maven-3
ENV M2_HOME=/usr/share/java/maven-3
ENV JENKINS_PORT=8080
#/root/.sdkman/candidates/groovy/current/bin/groovy

EXPOSE $JENKINS_PORT

CMD java -jar jenkins.war --httpPort=$JENKINS_PORT -Xmx2048m