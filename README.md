# <p align="center">Pine Framework</p>

Pine Framework is framework based on Java language.
Pine framework has a big mission, and it is, write business once independent of tools, framework, database, application
server,... then use the implemented business into different architecture or structure.

The framework comprised three main part as follows:

- core components
- core implementation component (like JEE, Spring, ...)
- product component

# <p align="center">Table of content</p>

- **[1 Pine Java Core](#1-Pine-Java-Core)**
- **[2 Tools](#2-Tools)**
    - **[2-1 Java](#2-1-Java)**
        - **[2-1-1 Windows](#2-1-1-Windows)**
        - **[2-1-2 Linux](#2-1-2-Linux)**
        - **[2-1-3 Test Java](#2-1-3-Test-Java)**
    - **[2-2 Maven](#2-2-Maven)**
        - **[2-2-1 Windows](#2-2-1-Windows)**
        - **[2-2-2 Linux](#2-2-2-Linux)**
        - **[2-2-3 Test Maven](#2-2-3-Test-Maven)**
    - **[2-3 Git](#2-3-Git)**
        - **[2-3-1 Windows](#2-3-1-Windows)**
        - **[2-3-2 Linux](#2-3-2-Linux)**
        - **[2-3-3 Test Git](#2-3-3-Test-git)**
        - **[2-3-4 GitHub CLI](#2-3-4-GitHub-CLI)**
    - **[2-4 SonarQube](#2-4-Sonarqube)**
        - **[2-4-1 Sonarqube Server](#2-4-1-Sonarqube-Server)**
        - **[2-4-2 Sonar Scanner](#2-4-2-Sonar-Scanner)**
    - **[2-5 Jenkins](#2-5-Jenkins)**
    - **[2-6 JFrog](#2-6-JFrog)**
    - **[2-7 IDE Setting](#2-7-IDE-Setting)**
        - **[2-7-1 Intellij IDEA](#2-7-1-Intellij-IDEA)**
    - **[2-8 Ngrok](#2-8-Ngrok)**
- **[3 Build](#3-Build-and-Test)**
- **[4 Install](#4-Install)**
- **[3 Pipeline](#5-Pipeline)**
    - **[3-1 Maven Pipeline](#5-1-Maven-Pipeline)**
    - **[3-1 Jenkins Pipeline](#5-2-Jenkins-Pipeline)**

---

# <span style="color: Crimson">1 Pine Java Core</span>

Pine Java Core is a basic module is included utils, helpers, abstracts and the other basic facilities.

Pine Core Java has modules as follows.

- helper
- document
- i18n 

# <span style="color: Crimson">2 Tools</span>

## <span style="color: RoyalBlue">2-1 Java</span>

### 2-1-1 Windows

[Download Java 17 in zip format.](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)

```shell
mkdir C:\sdk
# extract zip file to C:\sdk and rename it to jdk-17
set JAVA_HOME=C:\sdk\jdk-17
setx /M JAVA_HOME "%JAVA_HOME%"
setx /M PATH "%PATH%;%JAVA_HOME%\bin"
```

### 2-1-2 Linux

[Download Java 17 in tar.gz format.](https://www.oracle.com/java/technologies/downloads/#jdk17-linux)

```shell
sudo chown ${USER} -R /opt/
sudo chmod 765 -R /opt/
tar -xvf ./jdk-17_linux-x64_bin.tar.gz -C /opt/
mv jdk-17_linux-x64_bin jdk-17
sudo chmod +x -R /opt/java-17/bin/
echo "export JAVA_HOME=/opt/jdk-17" >> ${HOME}/.bashrc
sed -i 's/$PATH/$JAVA_HOME\/bin:$PATH/g' .bashrc #redhat
sed -i 's/PATH=/PATH=$JAVA_HOME\/bin:/' .bashrc #debian
source ~/.bashrc
```

### 2-1-3 Test Java

```shell
java -version
```

## <span style="color: RoyalBlue">2-2 Maven</span>

### 2-2-1 Windows

[Download Maven in zip format.](https://maven.apache.org/download.cgi)

```shell
mkdir C:\sdk
# extract zip file to C:\sdk and rename it to maven
set M2_HOME=C:\sdk\maven
setx /M M2_HOME "%M2_HOME%"
setx /M PATH "%PATH%;%M2_HOME%\bin"
```

### 2-2-2 Linux

[Download Maven in tar.gz format.](https://maven.apache.org/download.cgi)

```shell
sudo chown ${USER} -R /opt/
sudo chmod 765 -R /opt/
tar -xvf ./maven*.tar.gz -C /opt/
mv maven* maven
sudo chmod +x -R /opt/maven/bin/
echo "export M2_HOME=/opt/maven" >> ${HOME}/.bashrc
sed -i 's/$PATH/M2_HOME\/bin:$PATH/g' .bashrc #redhat
sed -i 's/PATH=/PATH=$M2_HOME\/bin:/' .bashrc #debian
source ~/.bashrc
```

### 2-2-3 Test Maven

```shell
mvn -version
```

## <span style="color: RoyalBlue">2-3 Git</span>

### 2-3-1 Windows

1. [download Git installer](https://git-scm.com/download/win)
2. install setup

### 2-3-2 Linux

[Guidance of how to download and install](https://git-scm.com/download/linux).

### 2-3-3 Test Git

```shell
git --version
```

### 2-3-4 GitHub CLI

[generate token by GitHub.com](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
[install GitHub CLI](https://github.com/cli/cli)

### 2-3-5 Test GitHub CLI

```shell
gh –-version
```

Create a text file named token.txt and copy token from GitHub and paste in the `token.txt`.

Login via GitHub CLI.

```shell
   gh auth login -p ssh -h github.com --with-token < token.txt
   gh repo list
```

Generate ssh keys by git tool (select one solution).

```shell
  ssh-keygen -t rsa -C "comment" #interactive mode
  ssh-keygen -t rsa -C "comment" -N '' -f ~/.ssh/id_rsa #without prompt
  add key: ssh-add ~/.ssh/id_rsa
```

Deploy public keys via GitHub CLI (select one solution).

```shell
  gh repo deploy-key add ~/.ssh/id_rsa.pub -R owner/repository-name -t key-title -w #deploy key into repository
  gh ssh-key add ~/.ssh/id_rsa.pub #deploy key into user
```

### 2-3-5 Test GitHub ssh

```shell
ssh -T git@github.com
```

## <span style="color: RoyalBlue">2-4 Sonarqube</span>

### 2-4-1 Sonarqube Server

[Download sonarqube](https://www.sonarqube.org/downloads/) then extract it.
In the extracted path execute the following command.

#### Windows

```shell
$SONARQUBE_HOME/bin/linux-x86-64/sonar.sh start
```

#### Linux

```shell
%SONARQUBE_HOME%\bin/windows-x86-64/StartSonar.bat
```

Browse SonarQube for localhost installation at `http://localhost:9000`.

- username: admin
- password: admin

Generate token at _**My Account > security > Generate Tokens**_ and add environment variable.

#### Windows

```shell
setx /M SONAR_TOKEN generated-token
setx /M SONAR_URL sonarqube-url
```

#### Linux

```shell
echo "export SONAR_TOKEN=generated-token" >> ${HOME}/.bashrc
echo "export SONAR_URL=sonarqube-url" >> ${HOME}/.bashrc
```

### 2-4-2 Sonar Scanner

[Download sonar scanner cli](https://binaries.sonarsource.com/?prefix=Distribution/sonar-scanner-cli/) and extract it,
then add the following environment variables.

#### Windows

```shell
set SONAR_SCANNER_HOME=extracted path
setx /M PATH "%PATH%;%SONAR_SCANNER_HOME%\bin"
```

#### Linux

```shell
echo "export SONAR_SCANNER_HOME=extracted path" >> ${HOME}/.bashrc
sed -i 's/$PATH/$SONAR_SCANNER_HOME\/bin:$PATH/g' .bashrc #redhat
sed -i 's/PATH=/PATH=$SONAR_SCANNER_HOME\/bin:/' .bashrc #debian
source ~/.bashrc
```

## <span style="color: RoyalBlue">2-5 Jenkins</span>

[Download jenkins as war file](https://www.jenkins.io/download/).

```shell
java -jar jenkins.war --httpPort=8080
```

Browse Jenkins for localhost installation at http://localhost:9090.

- username: admin
- password: look at the console

## <span style="color: RoyalBlue">2-6 JFrog</span>

[Download Jfrog](https://jfrog.com/download-jfrog-platform/) and extract it.
In the extracted path execute the following command.

#### Windows

```shell
set JFROG_HOME=extracted path
setx /M JFROG_HOME "%JFROG_HOME%"
%JFROG_HOME%\app\bin\artifactory.bat
```

#### Linux

```shell
echo "export JFROG_HOME=extracted path" >> ${HOME}/.bashrc
source ~/.bashrc
$JFROG_HOME/artifactory/app/bin/artifactoryctl
```

Browse JFrog for localhost installation at `http://localhost:8082/ui`.

- username: admin
- password: password

Change password at _**Administration > User Management > Users**_.

Get encrypted password from _**Edit Profile**_ menu.

Create repository by _**Quick Setup**_ menu.

## <span style="color: RoyalBlue">2-7 IDE Setting</span>

### 2-7-1 Intellij IDEA

#### Checkstyle

[Install google check style plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/versions).

Add customized checkstyle.xml to _**setting/preferences > Tools > Checkstyle > Configuration File**_.

Import customized checkstyle.xml to _**setting/preferences > Editor > Code Style > Schema**_.


#### Test Coverage

Click on _**Navigate > Search Everywhere**_, then type `Registry...`and in the registry, type `idea.coverage` then disable
    - idea.coverage.new.sampling.enable
    - idea.coverage.test.tracking.enable
    - idea.coverage.tracing.enable

## <span style="color: RoyalBlue">2-8 Ngrok</span>

[Go to Ngrok website](https://dashboard.ngrok.com/) and create an account.

Download ngrok then add ngrok to system path.
#### Windows
```shell
set NGROK_HOME=extracted-path
setx /M PATH "%PATH%;%NGROK_HOME%"
```

#### Linux

```shell
echo "export NGROK_HOME=extracted-path" >> ${HOME}/.bashrc
source ~/.bashrc
```

add token 
```shell
ngrok config add-authtoken <token>
```
Define tunnel like the following example in the config file.
windows: user-home\AppData\Local\ngrok\ngrok.yml

```
tunnels:
  jenkins:
    addr: 8080
    proto: http
  jfrog:
    addr: 8082
    proto: http
  sonar:
    addr: 9000
    proto: http
```
```shell
ngrok start --all 
```
Brows http://127.0.0.1:4040.

---

# <span style="color: Crimson">3 Build and Test</span>

``` shell
mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license
mvn test -s settings.xml
mvn checkstyle:check -s settings.xml -P checkstyle
mvn install -DskipTests=true -s settings.xml
mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report
mvn scm-publish:publish-scm -s settings.xml -P publish
```

---

# <span style="color: Crimson">4 Install</span>

``` shell
mvn validate
mvn clean install -DskipTests=true
```

---

# <span style="color: Crimson">5 Pipeline</span>

## <span style="color: RoyalBlue">5-1 Maven Pipeline</span>

``` shell
mvn validate
mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license
mvn test -s settings.xml
mvn checkstyle:check -s settings.xml -P checkstyle
mvn install -DskipTests=true -s settings.xml
mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report
mvn scm-publish:publish-scm -s settings.xml -P publish
mvn sonar:sonar -s settings.xml -P sonar
mvn deploy -s settings.xml -P jfrog
```

## <span style="color: RoyalBlue">5-2 Jenkins Pipeline</span>

1. install [Jenkins](#2-5-Jenkins), [JFrog](#2-5-JFrog), [Sonarqube](#2-4-Sonarqube) and [ngrok](#2-8-Ngrok)
2. setup JDK, Maven and Git
3. add `sonar_token`, `artifactory_password` and `github_token`.
2. generate ssh key by git and deploy the public key to the GitHub repository.
3. click on New Item menu in dashboard of [Jenkins](#2-5-Jenkins)
4. select Pipeline and then OK.
5. in the configuration page check `GitHub hook trigger for GITScm polling` item and insert the url of the project.
6. in Advanced Project Options section, select `Pipeline script from SCM` as pipeline definition.
    - select Git as an SCM
    - insert URL of repository in HTTPS format
    - add credentials (GitHub token as a secret text)
    - choose branch
    - insert script path (default is Jenkinsfile)
7. click on the save button to see the pipeline dashboard.
8. click on Build Now menu

---

#### <p align="center"> [Top](#Pine-Framework) </p>