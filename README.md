# Pine Framework

Pine Framework is framework based on Java language.

## Pine Java Core

Pine Java Core is a basic module is included utils, helpers, abstracts and the other basic facilities.

- Pine Helper

## Prerequisites

- Java 8
- Maven 3
- Git
- SonarQube

### Install Java

#### Windows

1. [download Java 17 in zip format](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
2. (optional) `mkdir C:\sdk`
3. (optional) extract zip file to C:\sdk and rename it to jdk-17
4. `set JAVA_HOME=C:\sdk\jdk-17`
5. `setx /M JAVA_HOME "%JAVA_HOME%"`
6. `setx /M PATH "%PATH%;%JAVA_HOME%\bin"`

#### Linux

1. [download Java 17 in tar.gz format](https://www.oracle.com/java/technologies/downloads/#jdk17-linux)
2. `sudo chown user-name /opt/`
3. `sudo chmod 765 /opt/`
4. `tar -xvf ./jdk*.tar.gz -C /opt/`
5. `mv jdk* jdk-17`
6. `echo "export JAVA_HOME=/opt/jdk-17" >> /home/user-name/.bashrc`
7. `sed -i 's/$PATH/$JAVA_HOME\/bin:$PATH/g' .bashrc `
8. `source ~/.bashrc`

#### Test Java

`java -version`

### Install Maven

#### Windows

1. [download Maven in zip format](https://maven.apache.org/download.cgi)
2. (optional) `mkdir C:\sdk`
3. (optional) extract zip file to C:\sdk and rename it to maven
4. `set M2_HOME=C:\sdk\maven`
5. `setx /M M2_HOME "%M2_HOME%"`
6. `setx /M PATH "%PATH%;%M2_HOME%\bin"`

#### Linux

1. [download Maven in tar.gz format](https://maven.apache.org/download.cgi)
2. `sudo chown user-name /opt/`
3. `sudo chmod 765 /opt/`
4. `tar -xvf ./maven*.tar.gz -C /opt/`
5. `mv maven* maven`
6. `echo "export M2_HOME=/opt/maven" >> /home/user-name/.bashrc`
7. `sed -i 's/$PATH/M2_HOME\/bin:$PATH/g' .bashrc `
8. `source ~/.bashrc`

#### Test Maven

`mvn -version`

### Install Git

#### Windows

1. [download Git installer](https://git-scm.com/download/win)
2. install setup

#### Linux

[how to download and install](https://git-scm.com/download/linux)

#### Test Git

`git --version`

### Install SonarQube

## IDE Setting

### Intellij IDEA

#### checkstyle

1. [Install google check style plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/versions)
2. Add customized checkstyle.xml to _**setting/preferences > Tools > Checkstyle > Configuration File**_
   
<center><img height="300" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/idea-checkstyle-001.png" width="300"/></center>

3. Import customized checkstyle.xml to _**setting/preferences > Editor > Code Style > Schema**_

<center><img height="300" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/idea-checkstyle-002.png" width="300"/></center>

#### Test Coverage

1. Click on _**Navigate > Search Everywhere**_, then type `Registry...`.
2. In registry, type `idea.coverage` then disable

    - idea.coverage.new.sampling.enable
    - idea.coverage.test.tracking.enable
    - idea.coverage.tracing.enable

## Build and Test

`mvn clean package`

## Build and Install

`mvn clean install -DskipTests=true`
