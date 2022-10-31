FROM openjdk:8-alpine
MAINTAINER samanalishiri@gmail.com

RUN apk --no-cache add ca-certificates wget shadow

ARG USER=nexus
ARG GROUP=ops
ARG USER_HOME=/home/$USER

ARG APP_VERSION=3.42.0-01
ARG APP_HOME=/opt/sonatype

ENV USER=$USER
ENV GROUP=$GROUP
ENV HOME=$USER_HOME

ENV APP_HOME=$APP_HOME
ENV APP_PORT=8081

RUN groupadd $GROUP
RUN useradd -d /home/$USER -g $GROUP -m -s /bin/bash $USER

RUN mkdir -p  $APP_HOME
RUN chown -R $USER:$GROUP $APP_HOME

RUN wget -q -O /tmp/nexus-unix.tar.gz https://download.sonatype.com/nexus/3/nexus-$APP_VERSION-unix.tar.gz
RUN tar -xvzf /tmp/nexus-unix.tar.gz -C $APP_HOME
RUN mv $APP_HOME/nexus-$APP_VERSION $APP_HOME/nexus
RUN rm -rf /tmp/*

EXPOSE $APP_PORT

USER $USER

CMD $APP_HOME/nexus/bin/nexus run