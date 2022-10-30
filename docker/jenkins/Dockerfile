FROM alpine:latest
MAINTAINER samanalishiri@gmail.com

ARG USER=jenkins
ARG GROUP=ops
ARG USER_HOME=/home/${USER}/

ARG JENKINS_VERSION=latest
ARG JENKINS_HOME=/opt/jenkins

ARG GLIBC_PKG_VERSION=2.35-r0

RUN apk add ca-certificates wget curl openrc openssh openjdk17 git maven github-cli shadow

RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_PKG_VERSION/glibc-$GLIBC_PKG_VERSION.apk
RUN apk add glibc-$GLIBC_PKG_VERSION.apk

RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_PKG_VERSION/glibc-bin-$GLIBC_PKG_VERSION.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_PKG_VERSION/glibc-i18n-$GLIBC_PKG_VERSION.apk
RUN apk add glibc-bin-$GLIBC_PKG_VERSION.apk glibc-i18n-$GLIBC_PKG_VERSION.apk

RUN apk add ttf-dejavu
RUN apk update

RUN echo "en_US" > ~/locale.md
RUN echo "fa_IR" >> ~/locale.md
RUN cat ~/locale.md | xargs -i /usr/glibc-compat/bin/localedef -i {} -f UTF-8 {}.UTF-8

RUN groupadd ${GROUP}
RUN useradd -d /home/${USER} -g ${GROUP} -m -s /bin/bash ${USER}

RUN mkdir -p  $JENKINS_HOME
RUN chown -R ${USER}:${GROUP} $JENKINS_HOME

RUN wget  -q -O $JENKINS_HOME/jenkins.war https://get.jenkins.io/war/$JENKINS_VERSION/jenkins.war

COPY init.sh ${JENKINS_HOME}

ENV USER=${USER}
ENV GROUP=${GROUP}
ENV HOME=${USER_HOME}

ENV JENKINS_HOME=${JENKINS_HOME}
ENV JENKINS_PORT=8080

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV M2_HOME=/usr/share/java/maven-3

ENV LANG=en_US.UTF-8

ENV GITHUB_EMAIL=samanalishiri@gmail.com
ENV GITHUB_USER=samanalishiri
ENV GITHUB_JENKINS_TOKEN=empty
ENV GITHUB_ARTIFACTORY_URL=empty

EXPOSE $JENKINS_PORT

USER ${USER}
CMD ${JENKINS_HOME}/init.sh