<style>
red { color: Crimson }
blue { color: Royalblue }
black { color: Black }
</style>

# <p align="center"><black>Pine Framework</black></p>

Pine Framework is framework based on Java language.
Pine framework has a big mission, and it is, write business once independent of tools, framework, database, application server,... then use the implemented business into different architecture or structure.

The framework comprised three main part as follows:

- core components 
- core implementation component (like JEE, Spring, ...)
- product component

# <p align="center"><black>Table of content</black></p>

- **[1 Pine Java Core](#1-Pine-Java-Core)**
- **[2 Tools](#2-Tools)**
  - **[2.1 Java](#2.1-Java)**
     - **[2.1.1 Windows](README.md#2.1.1-Windows)**
     - **[2.1.2 Linux](README.md#2.1.2-Linux)**
     - **[2.1.3 Test Java](README.md#2.1.3-Test-Java)**
  - **[2.2 Maven](README.md#2.2-Maven)**
     - **[2.2.1 Windows](README.md#2.2.1-Windows)**
     - **[2.2.2 Linux](README.md#2.2.2-Linux)**
     - **[2.2.3 Test Maven](README.md#2.2.3-Test-Maven)**
  - **[Git](README.md#Git)**
     - **[Windows](README.md#Windows)**
     - **[Linux](README.md#Linux)**
     - **[Test Git](README.md#Test git)**
  - **[SonarQube](README.md#Sonarqube)**
     - **[Sonarqube Server](README.md#Sonarqube Server)**
     - **[Sonar Scanner](README.md#Sonar Scanner)**
  - **[Jenkins](README.md#Jenkins)**
  - **[JFrog](README.md#JFrog)**
  - **[IDE Setting](README.md#IDE Setting)**
     - **[Intellij IDEA](README.md#Intellij IDEA)**
     - **[Checkstyle](README.md#Checkstyle)**
     - **[Test Coverage](README.md#Test Coverage)**
- **[Pipeline](README.md#<red>3.-Pipeline</red>)**
- **[Build](README.md#<red>4.-Build</red>)**
- **[Install](README.md#<red>5.-Install</red>)**


## <red>1 Pine Java Core</red>

  Pine Java Core is a basic module is included utils, helpers, abstracts and the other basic facilities.

  Pine Core Java has modules as follows.

- helper
- document

## 2 Tools

### 2.1 Java

#### 2.1.1 Windows

1. [download Java 17 in zip format](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
2. `mkdir C:\sdk`
3.  extract zip file to C:\sdk and rename it to jdk-17
4. `set JAVA_HOME=C:\sdk\jdk-17`
5. `setx /M JAVA_HOME "%JAVA_HOME%"`
6. `setx /M PATH "%PATH%;%JAVA_HOME%\bin"`

#### 2.1.2 Linux

1. [download Java 17 in tar.gz format](https://www.oracle.com/java/technologies/downloads/#jdk17-linux)
2. `sudo chown user-name /opt/`
3. `sudo chmod 765 /opt/`
4. `tar -xvf ./jdk*.tar.gz -C /opt/`
5. `mv jdk* jdk-17`
6. `echo "export JAVA_HOME=/opt/jdk-17" >> /home/user-name/.bashrc`
7. `sed -i 's/$PATH/$JAVA_HOME\/bin:$PATH/g' .bashrc `
8. `source ~/.bashrc`

#### 2.1.3 Test Java

`java -version`

### 2.2 Maven

#### 2.2.1 Windows

1. [download Maven in zip format](https://maven.apache.org/download.cgi)
2. `mkdir C:\sdk`
3. extract zip file to C:\sdk and rename it to maven
4. `set M2_HOME=C:\sdk\maven`
5. `setx /M M2_HOME "%M2_HOME%"`
6. `setx /M PATH "%PATH%;%M2_HOME%\bin"`

#### 2.2.2 Linux

1. [download Maven in tar.gz format](https://maven.apache.org/download.cgi)
2. `sudo chown user-name /opt/`
3. `sudo chmod 765 /opt/`
4. `tar -xvf ./maven*.tar.gz -C /opt/`
5. `mv maven* maven`
6. `echo "export M2_HOME=/opt/maven" >> /home/user-name/.bashrc`
7. `sed -i 's/$PATH/M2_HOME\/bin:$PATH/g' .bashrc `
8. `source ~/.bashrc`

#### 2.2.3 Test Maven

`mvn -version`

### Git

#### Windows

1. [download Git installer](https://git-scm.com/download/win)
2. install setup

#### Linux

[how to download and install](https://git-scm.com/download/linux)

#### Test Git

`git --version`

### Sonarqube

#### Sonarqube Server

1. [download sonarqube](https://www.sonarqube.org/downloads/) then extract it.
2. In the extracted path execute the following command.
    - Linux: bin/linux-x86-64/sonar.sh start
    - Windows: bin/windows-x86-64/StartSonar.bat
3. Browse SonarQube for localhost installation at http://localhost:9000.
    - username: admin
    - password: admin

Generate token at _**administration > security > users > Tokens**_ and add environment variable.

**Windows**

- setx /M SONAR_TOKEN generated-token
- setx /M SONAR_URL sonarqube-url

**Linux**

- echo "export SONAR_TOKEN=generated-token" >> /home/user-name/.bashrc
- echo "export SONAR_URL=sonarqube-url" >> /home/user-name/.bashrc

#### Sonar Scanner

[download sonar scanner cli](https://binaries.sonarsource.com/?prefix=Distribution/sonar-scanner-cli/) and extract it,
then add environment variable.

**Windows**

- set SONAR_SCANNER_HOME=extracted path
- setx /M PATH "%PATH%;%SONAR_SCANNER_HOME%\bin"

**Linux**

- echo "export SONAR_SCANNER_HOME=extracted path" >> /home/user-name/.bashrc
- sed -i 's/$PATH/$SONAR_SCANNER_HOME\/bin:$PATH/g' .bashrc
- source ~/.bashrc

### Jenkins

1. [download jenkins as war file](https://www.jenkins.io/download/)
2. execute `java -jar jenkins.war --httpPort=9090`
3. Browse Jenkins for localhost installation at http://localhost:9090.
    - username: admin
    - password: look at the console

### JFrog

1. [download Jfrog](https://jfrog.com/download-jfrog-platform/) and extract it.
2. In the extracted path execute the following command.
    - Linux: $JFROG_HOME/artifactory/app/bin/artifactoryctl
    - Windows: $JFROG_HOME\app\bin\artifactory.bat
      3.Browse JFrog for localhost installation at http://localhost:8082/ui.
    - username: admin
    - password: password

**Windows**

- set JFROG_HOME=extracted path
- setx /M JFROG_HOME "%JFROG_HOME%"

**Linux**

- echo "export JFROG_HOME=extracted path" >> /home/user-name/.bashrc
- source ~/.bashrc

## IDE Setting

### Intellij IDEA

#### Checkstyle

1. [install google check style plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/versions)
2. add customized checkstyle.xml to _**setting/preferences > Tools > Checkstyle > Configuration File**_

<p align="center">   
<img height="300" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/idea-checkstyle-001.png" width="300"/>
</p>

3. import customized checkstyle.xml to _**setting/preferences > Editor > Code Style > Schema**_

<p align="center">
<img height="300" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/idea-checkstyle-002.png" width="300"/>
</p>

#### Test Coverage

1. Click on _**Navigate > Search Everywhere**_, then type `Registry...`.
2. In registry, type `idea.coverage` then disable
    - idea.coverage.new.sampling.enable
    - idea.coverage.test.tracking.enable
    - idea.coverage.tracing.enable

## <red>3 Pipeline</red>

## <red>4 Build</red>

- `mvn clean package -DskipTests=true -s settings.xml`
- `mvn test -s settings.xml`
- `mvn checkstyle:check -s settings.xml`
- `mvn site:site site:stage -s settings.xml`
- `mvn scm-publish:publish-scm -s settings.xml`
- `mvn sonar:sonar -s settings.xml`
- `mvn deploy -s settings.xml`

## <red>5 Install</red>

`mvn clean install -DskipTests=true`


[Top](README.md#<p align="center"><black>Pine Framework</black></p>)
