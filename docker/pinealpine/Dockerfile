FROM alpine:latest
MAINTAINER samanalishiri@gmail.com

ARG GLIBC_PKG_VERSION=2.35-r0

RUN apk --no-cache add ca-certificates wget curl openrc openssh openjdk17 git maven github-cli shadow

RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_PKG_VERSION/glibc-$GLIBC_PKG_VERSION.apk
RUN apk --no-cache add glibc-$GLIBC_PKG_VERSION.apk

RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_PKG_VERSION/glibc-bin-$GLIBC_PKG_VERSION.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_PKG_VERSION/glibc-i18n-$GLIBC_PKG_VERSION.apk
RUN apk --no-cache add glibc-bin-$GLIBC_PKG_VERSION.apk glibc-i18n-$GLIBC_PKG_VERSION.apk

RUN apk --no-cache add ttf-dejavu
RUN apk update

RUN echo "en_US" > ~/locale.md
RUN echo "fa_IR" >> ~/locale.md
RUN cat ~/locale.md | xargs -i /usr/glibc-compat/bin/localedef -i {} -f UTF-8 {}.UTF-8

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV M2_HOME=/usr/share/java/maven-3
ENV LANG=en_US.UTF-8