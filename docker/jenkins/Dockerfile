FROM samanalishiri/pine-alpine

ARG USER=jenkins
ARG GROUP=ops
ARG USER_HOME=/home/$USER

ARG APP_VERSION=latest
ARG APP_HOME=/opt/jenkins

ENV USER=$USER
ENV GROUP=$GROUP
ENV HOME=$USER_HOME

ENV APP_HOME=$APP_HOME
ENV APP_PORT=8080

ENV GITHUB_EMAIL=empty
ENV GITHUB_USERNAME=empt
ENV GITHUB_TOKEN=empty
ENV GITHUB_URL=empty

RUN groupadd $GROUP
RUN useradd -d /home/$USER -g $GROUP -m -s /bin/bash $USER

RUN mkdir -p  $APP_HOME
RUN chown -R $USER:$GROUP $APP_HOME

RUN wget  -q -O $APP_HOME/jenkins.war https://get.jenkins.io/war/$APP_VERSION/jenkins.war
COPY init.sh $APP_HOME

EXPOSE $APP_PORT

USER $USER
CMD $APP_HOME/init.sh