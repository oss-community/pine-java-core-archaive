FROM alpine:latest
MAINTAINER samanalishiri@gmail.com

ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US.UTF-8

USER root
RUN apk update
RUN apk add ca-certificates wget curl openrc openssh openjdk17 git maven
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-2.25-r0.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-bin-2.25-r0.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-i18n-2.25-r0.apk
RUN apk add glibc-bin-2.25-r0.apk glibc-i18n-2.25-r0.apk glibc-2.25-r0.apk

RUN apk add ttf-dejavu

RUN echo "en_US" > ~/locale.md
RUN echo "fa_IR" >> ~/locale.md
RUN cat ~/locale.md | xargs -i /usr/glibc-compat/bin/localedef -i {} -f UTF-8 {}.UTF-8
