FROM alpine:latest
MAINTAINER samanalishiri@gmail.com

USER root
RUN apk update
RUN apk add ca-certificates openjdk17 curl git maven
RUN apk update
RUN java -version
RUN mvn -version
RUN git --version

