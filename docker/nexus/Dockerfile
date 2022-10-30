FROM openjdk:8-alpine

ARG USER=nexus
ARG GROUP=nexus
ARG NEXUS_VERSION=3.42.0-01

RUN apk update
RUN apk add ca-certificates wget curl ca-certificates wget curl shadow
RUN apk update

RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-2.25-r0.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-bin-2.25-r0.apk
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.25-r0/glibc-i18n-2.25-r0.apk
RUN apk add glibc-bin-2.25-r0.apk glibc-i18n-2.25-r0.apk glibc-2.25-r0.apk
RUN apk add ttf-dejavu

RUN echo "en_US" > ~/locale.md
RUN echo "fa_IR" >> ~/locale.md
RUN cat ~/locale.md | xargs -i /usr/glibc-compat/bin/localedef -i {} -f UTF-8 {}.UTF-8

RUN groupadd ${GROUP}
RUN useradd -d /home/${USER} -g ${GROUP} -m -s /bin/bash ${USER}

RUN mkdir -p /opt/sonatype
RUN chown -R ${GROUP}:${USER} /opt/sonatype

RUN curl -fsSL https://download.sonatype.com/nexus/3/nexus-${NEXUS_VERSION}-unix.tar.gz -o /tmp/nexus-${NEXUS_VERSION}-unix.tar.gz
RUN tar -xvzf /tmp/nexus-${NEXUS_VERSION}-unix.tar.gz -C /opt/sonatype
RUN mv /opt/sonatype/nexus-${NEXUS_VERSION} /opt/sonatype/nexus
RUN ln -s /opt/sonatype/sonatype-work/nexus3 /nexus-data

RUN chown -R nexus:nexus /opt /nexus-data

RUN rm -rf /tmp/*

USER nexus

ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US.UTF-8

CMD ["/opt/sonatype/nexus/bin/nexus","run"]