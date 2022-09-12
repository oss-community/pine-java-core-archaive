# <p align="center">Pine Framework</p>

Pine Framework is framework based on Java language.
Pine framework has a big mission, and it is, write business once independent of tools, framework, database, application
server,... then use the implemented business into different architecture or structure.

The framework comprised three main part as follows:

- core components
- core implementation component (like JEE, Spring, ...)
- product component

[Develper document](https://github.com/saman-oss/pine-java-core/blob/main/document/docs/pine-framework-v1.pdf).

---

## <p align="center">Table of content</p>

- **[Pine Java Core](#Pine-Java-Core)**
- **[Tools](#Tools)**
    - **[Java](#Java)**
        - **[Windows](#Windows)**
        - **[Linux](#Linux)**
        - **[Test Java](#2Test-Java)**
    - **[Maven](#Maven)**
        - **[Windows](#Windows)**
        - **[Linux](#Linux)**
        - **[Test Maven](#Test-Maven)**
    - **[Git](#Git)**
        - **[Windows](#Windows)**
        - **[Linux](#Linux)**
        - **[Test Git](#Test-git)**
        - **[GitHub CLI](#GitHub-CLI)**
    - **[SonarQube](#Sonarqube)**
        - **[Sonarqube Server](#Sonarqube-Server)**
        - **[Sonar Scanner](#Sonar-Scanner)**
    - **[Jenkins](#Jenkins)**
    - **[JFrog](#JFrog)**
    - **[IDE Setting](#IDE-Setting)**
        - **[Intellij IDEA](#Intellij-IDEA)**
    - **[Ngrok](#Ngrok)**
- **[Build](#Build-and-Test)**
- **[Install](#Install)**
- **[Pipeline](#Pipeline)**
    - **[Maven Pipeline](#Maven-Pipeline)**
    - **[Jenkins Pipeline](#Jenkins-Pipeline)**

---

# <p align="center"><span style="color: Crimson">Pine Java Core</span></p>

Pine Java Core is a basic module is included utils, helpers, abstracts and the other basic facilities.

Pine Core Java has modules as follows.

- helper
- document
- i18n

## <span style="color: Crimson">Prerequisite</span>
- Java
- Maven
- Git


## <span style="color: Crimson">Tools</span>
This tolls use for build and pipeline.

### <span style="color: RoyalBlue">Java</span>

#### Windows

Download [Java 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows) in zip format.

```shell
mkdir C:\sdk
# extract zip file to C:\sdk and rename it to jdk-17
set JAVA_HOME=C:\sdk\jdk-17
setx /M JAVA_HOME "%JAVA_HOME%"
setx /M PATH "%PATH%;%JAVA_HOME%\bin"
```

#### Linux

Download [Java 17](https://www.oracle.com/java/technologies/downloads/#jdk17-linux) in tar.gz format.

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

#### Test Java

```shell
java -version
```

### <span style="color: RoyalBlue">2-2 Maven</span>

#### Windows

Download [Maven](https://maven.apache.org/download.cgi) in zip format.

```shell
mkdir C:\sdk
# extract zip file to C:\sdk and rename it to maven
set M2_HOME=C:\sdk\maven
setx /M M2_HOME "%M2_HOME%"
setx /M PATH "%PATH%;%M2_HOME%\bin"
```

#### Linux

Download [Maven](https://maven.apache.org/download.cgi) in tar.gz format.

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

#### Test Maven

```shell
mvn -version
```

### <span style="color: RoyalBlue">2-3 Git</span>

#### Windows

1. download [Git](https://git-scm.com/download/win) installer
2. install setup

#### Linux

[Guidance](https://git-scm.com/download/linux) of how to download and install.

#### Test Git

```shell
git --version
```

#### GitHub CLI

Install [GitHub CLI](https://github.com/cli/cli)

#### Test GitHub CLI

```shell
gh –-version
```

[Generate token by GitHub.com](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

Create a text file named token.txt and copy the token from GitHub and paste in the `token.txt`.

Login via GitHub CLI.

```shell
   gh auth login -p ssh -h github.com --with-token < token.txt
   gh repo list
```

Generate ssh keys by git tool (select one solution).

```shell
  ssh-keygen -t rsa -C "comment" #interactive mode, solution 1
  ssh-keygen -t rsa -C "comment" -N '' -f ~/.ssh/id_rsa #without prompt, solution 2
  add key: ssh-add ~/.ssh/id_rsa
```

Deploy public keys via GitHub CLI (select one solution).

```shell
  gh repo deploy-key add ~/.ssh/id_rsa.pub -R owner/repository-name -t key-title -w #deploy key into repository, solution 1
  gh ssh-key add ~/.ssh/id_rsa.pub #deploy key into user, solution 2
  ssh -T git@github.com
```

### <span style="color: RoyalBlue">Sonarqube</span>

#### Sonarqube Server

Download [sonarqube](https://www.sonarqube.org/downloads/) then extract it.
In the extracted path execute the following command.

##### Windows

```shell
$SONARQUBE_HOME/bin/linux-x86-64/sonar.sh start
```

##### Linux

```shell
%SONARQUBE_HOME%\bin/windows-x86-64/StartSonar.bat
```

Browse SonarQube for localhost installation at `http://localhost:9000`.

- username: admin
- password: admin

Generate token at _**My Account > security > Generate Tokens**_ and add environment variable.

##### Windows

```shell
setx /M SONAR_TOKEN generated-token
setx /M SONAR_URL sonarqube-url
```

##### Linux

```shell
echo "export SONAR_TOKEN=generated-token" >> ${HOME}/.bashrc
echo "export SONAR_URL=sonarqube-url" >> ${HOME}/.bashrc
```

#### Sonar Scanner

Download [sonar scanner cli](https://binaries.sonarsource.com/?prefix=Distribution/sonar-scanner-cli/) and extract it,
then add the following environment variables.

##### Windows

```shell
set SONAR_SCANNER_HOME=extracted path
setx /M PATH "%PATH%;%SONAR_SCANNER_HOME%\bin"
```

##### Linux

```shell
echo "export SONAR_SCANNER_HOME=extracted path" >> ${HOME}/.bashrc
sed -i 's/$PATH/$SONAR_SCANNER_HOME\/bin:$PATH/g' .bashrc #redhat
sed -i 's/PATH=/PATH=$SONAR_SCANNER_HOME\/bin:/' .bashrc #debian
source ~/.bashrc
```

### <span style="color: RoyalBlue">Jenkins</span>
Download [jenkins](https://www.jenkins.io/download/) as war file.

```shell
java -jar jenkins.war --httpPort=8080
```

Browse Jenkins for localhost installation at http://localhost:9090.

- username: admin
- password: look at the console

### <span style="color: RoyalBlue">JFrog</span>

Download [Jfrog](https://jfrog.com/download-jfrog-platform/) and extract it.
In the extracted path execute the following command.

##### Windows

```shell
set JFROG_HOME=extracted path
setx /M JFROG_HOME "%JFROG_HOME%"
%JFROG_HOME%\app\bin\artifactory.bat
```

##### Linux

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

### <span style="color: RoyalBlue">IDE Setting</span>

#### Intellij IDEA

##### Checkstyle

Install [google check style](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/versions) plugin.

Add customized checkstyle.xml to _**setting/preferences > Tools > Checkstyle > Configuration File**_.

Import customized checkstyle.xml to _**setting/preferences > Editor > Code Style > Schema**_.

##### Test Coverage

Click on _**Navigate > Search Everywhere**_, then type `Registry...`and in the registry, type `idea.coverage` then
disable
- idea.coverage.new.sampling.enable
- idea.coverage.test.tracking.enable
- idea.coverage.tracing.enable

### <span style="color: RoyalBlue">Ngrok</span>

Go to [Ngrok](https://dashboard.ngrok.com/) website and create an account.

Download ngrok then add ngrok to system path.

##### Windows

```shell
set NGROK_HOME=extracted-path
setx /M PATH "%PATH%;%NGROK_HOME%"
```

##### Linux

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

## <span style="color: Crimson">Build and Test</span>

``` shell
mvn validate
mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license
mvn test -s settings.xml
mvn checkstyle:check -s settings.xml -P checkstyle
mvn install -DskipTests=true -s settings.xml
mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report
mvn scm-publish:publish-scm -s settings.xml -P publish
```

### <span style="color: RoyalBlue">Site</span>

View generated [documents](https://saman-oss.github.io/pine-java-core/) of project.

---

## <span style="color: Crimson">Install</span>

``` shell
mvn validate
mvn clean install -DskipTests=true
```

---

## <span style="color: Crimson">Pipeline</span>

### <span style="color: RoyalBlue">Maven Pipeline</span>

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
mvn deploy -s settings.xml -P github
```

### <span style="color: RoyalBlue">Jenkins Pipeline</span>

1. install [Jenkins](#Jenkins), [JFrog](#JFrog), [Sonarqube](#Sonarqube) and [ngrok](#Ngrok)
2. setup JDK, Maven and Git
3. add `sonar_token`, `artifactory_password` and `github_token`.
4. generate ssh key by git and deploy the public key to the GitHub repository.
5. click on New Item menu in dashboard of [Jenkins](#Jenkins)
6. select Pipeline and then OK.
7. in the configuration page check `GitHub hook trigger for GITScm polling` item and insert the url of the project.
8. in Advanced Project Options section, select `Pipeline script from SCM` as pipeline definition.
    - select Git as an SCM
    - insert URL of repository in HTTPS format
    - add credentials (GitHub token as a secret text)
    - choose branch
    - insert script path (default is Jenkinsfile)
9. click on the save button to see the pipeline dashboard.
10. click on Build Now menu

---

#### <p align="center"> [Top](#Pine-Framework) </p>