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
    - **[3-1 Jenkins Pipeline With Trigger](#5-3-Jenkins-Pipeline-With-Trigger)**

---

# <span style="color: Crimson">1 Pine Java Core</span>

Pine Java Core is a basic module is included utils, helpers, abstracts and the other basic facilities.

Pine Core Java has modules as follows.

- helper
- document

# <span style="color: Crimson">2 Tools</span>

## <span style="color: RoyalBlue">2-1 Java</span>

### 2-1-1 Windows

[Download Java 17 in zip format.](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)

```bash
mkdir C:\sdk
# extract zip file to C:\sdk and rename it to jdk-17
set JAVA_HOME=C:\sdk\jdk-17
setx /M JAVA_HOME "%JAVA_HOME%"
setx /M PATH "%PATH%;%JAVA_HOME%\bin"
```

### 2-1-2 Linux

[Download Java 17 in tar.gz format.](https://www.oracle.com/java/technologies/downloads/#jdk17-linux)

```bash
sudo chown user-name /opt/
sudo chmod 765 /opt/
tar -xvf ./jdk-17_linux-x64_bin.tar.gz -C /opt/
mv jdk-17_linux-x64_bin jdk-17
echo "export JAVA_HOME=/opt/jdk-17" >> /home/user-name/.bashrc
sed -i 's/$PATH/$JAVA_HOME\/bin:$PATH/g' .bashrc #redhat
sed -i 's/PATH=/PATH=$JAVA_HOME\/bin:/' .bashrc #debian
source ~/.bashrc
```

### 2-1-3 Test Java

```bash
java -version
```

## <span style="color: RoyalBlue">2-2 Maven</span>

### 2-2-1 Windows

[Download Maven in zip format.](https://maven.apache.org/download.cgi)

```bash
mkdir C:\sdk
# extract zip file to C:\sdk and rename it to maven
set M2_HOME=C:\sdk\maven
setx /M M2_HOME "%M2_HOME%"
setx /M PATH "%PATH%;%M2_HOME%\bin"
```

### 2-2-2 Linux


[Download Maven in tar.gz format.](https://maven.apache.org/download.cgi)

```bash
sudo chown user-name /opt/
sudo chmod 765 /opt/
tar -xvf ./maven*.tar.gz -C /opt/
mv maven* maven
echo "export M2_HOME=/opt/maven" >> /home/user-name/.bashrc
sed -i 's/$PATH/M2_HOME\/bin:$PATH/g' .bashrc #redhat
sed -i 's/PATH=/PATH=$M2_HOME\/bin:/' .bashrc #debian
source ~/.bashrc
```

### 2-2-3 Test Maven

```bash
mvn -version
```

## <span style="color: RoyalBlue">2-3 Git</span>

### 2-3-1 Windows

1. [download Git installer](https://git-scm.com/download/win)
2. install setup

### 2-3-2 Linux

[Guidance of how to download and install](https://git-scm.com/download/linux).

### 2-3-3 Test Git

```bash
git --version
```
### 2-3-4 GitHub CLI
1. generate token by GitHub.com 
2. install GitHub CLI 
3. run `gh –version`
4. create a text file named token.txt 
5. copy token from GitHub and paste in the token.txt 
6. login via GitHub CLI 
   - gh auth login -p ssh -h github.com --with-token < token.txt
   - gh repo list
7. generate ssh keys by git tool
   - interactive mode: ssh-keygen -t rsa -C "comment"
   - without prompt: ssh-keygen -t rsa -C "comment" -N '' -f ~/.ssh//id_rsa. <<< y
8. deploy public keys via GitHub CLI
   - gh repo deploy-key add ./id_rsa.pub -R owner/repository-name -t key-title -w


## <span style="color: RoyalBlue">2-4 Sonarqube</span>

### 2-4-1 Sonarqube Server

1. [download sonarqube](https://www.sonarqube.org/downloads/) then extract it
2. in the extracted path execute the following command
    - Linux: `bin/linux-x86-64/sonar.sh start`
    - Windows: `bin/windows-x86-64/StartSonar.bat`
3. browse SonarQube for localhost installation at `http://localhost:9000`.
    - username: admin
    - password: admin

Generate token at _**My Account > security > Generate Tokens**_ and add environment variable.

#### Windows

```bash
setx /M SONAR_TOKEN generated-token
setx /M SONAR_URL sonarqube-url
```

#### Linux

```bash
echo "export SONAR_TOKEN=generated-token" >> /home/user-name/.bashrc
echo "export SONAR_URL=sonarqube-url" >> /home/user-name/.bashrc
```

### 2-4-2 Sonar Scanner

[Download sonar scanner cli](https://binaries.sonarsource.com/?prefix=Distribution/sonar-scanner-cli/) and extract it,
then add the following environment variables.

#### Windows

```bash
set SONAR_SCANNER_HOME=extracted path
setx /M PATH "%PATH%;%SONAR_SCANNER_HOME%\bin"
```

#### Linux

```bash
echo "export SONAR_SCANNER_HOME=extracted path" >> /home/user-name/.bashrc
sed -i 's/$PATH/$SONAR_SCANNER_HOME\/bin:$PATH/g' .bashrc #redhat
sed -i 's/PATH=/PATH=$SONAR_SCANNER_HOME\/bin:/' .bashrc #debian
source ~/.bashrc
```

## <span style="color: RoyalBlue">2-5 Jenkins</span>

1. [download jenkins as war file](https://www.jenkins.io/download/)
2. `java -jar jenkins.war --httpPort=8080`
3. browse Jenkins for localhost installation at http://localhost:9090.
    - username: admin
    - password: look at the console

## <span style="color: RoyalBlue">2-6 JFrog</span>

1. [download Jfrog](https://jfrog.com/download-jfrog-platform/) and extract it
2. in the extracted path execute the following command
    - Linux: `$JFROG_HOME/artifactory/app/bin/artifactoryctl`
    - Windows: `%JFROG_HOME%\app\bin\artifactory.bat`
3. browse JFrog for localhost installation at `http://localhost:8082/ui`.
    - username: admin
    - password: password
4. change password at _**Administration > User Management > Users**_ 
5. get encrypted password from _**Edit Profile**_ menu
6. create repository by _**Quick Setup**_ menu

##### Windows

```bash
set JFROG_HOME=extracted path
setx /M JFROG_HOME "%JFROG_HOME%"
```

##### Linux

```bash
echo "export JFROG_HOME=extracted path" >> /home/user-name/.bashrc
source ~/.bashrc
```

## <span style="color: RoyalBlue">2-7 IDE Setting</span>

### 2-7-1 Intellij IDEA

#### Checkstyle

1. [install google check style plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/versions)
2. add customized checkstyle.xml to _**setting/preferences > Tools > Checkstyle > Configuration File**_
3. import customized checkstyle.xml to _**setting/preferences > Editor > Code Style > Schema**_

#### Test Coverage

1. click on _**Navigate > Search Everywhere**_, then type `Registry...`.
2. in registry, type `idea.coverage` then disable
    - idea.coverage.new.sampling.enable
    - idea.coverage.test.tracking.enable
    - idea.coverage.tracing.enable

## <span style="color: RoyalBlue">2-8 Ngrok</span>

1. [go to Ngrok website](https://dashboard.ngrok.com/)
2. create an account
3. download ngrok
4. add ngrok to system path
5. add token `ngrok config add-authtoken <token>`
6. define tunnel like the following example

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

7. `ngrok start --all`
8. brows http://127.0.0.1:4040
---

# <span style="color: Crimson">3 Build and Test</span>

``` bash
mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license
mvn test -s settings.xml
mvn checkstyle:check -s settings.xml -P checkstyle
mvn install -DskipTests=true -s settings.xml
mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report
mvn scm-publish:publish-scm -s settings.xml -P publish
  ```

---

# <span style="color: Crimson">4 Install</span>

``` bash
mvn clean install -DskipTests=true
```

---

# <span style="color: Crimson">5 Pipeline</span>

## <span style="color: RoyalBlue">5-1 Maven Pipeline</span>

``` bash
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

1. install Jenkins, Jfrog, Sonarqube and ngrok
2. generate ssh key by git and deploy the public key to the GitHub repository.
2. click on New Item menu in dashboard of [Jenkins](#2-5-Jenkins)
2. select Pipeline and then OK.
3. in the configuration page check `GitHub hook trigger for GITScm polling` item and insert the url of the project.
4. in Advanced Project Options section select Pipeline script from SCM as pipeline definition.
    - select Git as an SCM
    - insert URL of repository in HTTPS format
    - add credentials (GitHub token as a secret text)
    - choose branch
    - insert script path (default is Jenkinsfile)
5. click on the save button to see the pipeline dashboard.
6. click on Build Now menu

## <span style="color: RoyalBlue">5-3 Jenkins Pipeline With Trigger</span>

1. install Jenkins, Jfrog, Sonarqube and ngrok
2. generate ssh key by git and deploy the public key to the GitHub repository.
3. add webhook to your GitHub repository
    - Url of Jenkins: https://jenkins-domain/github-webhook/
4. click on New Item menu in dashboard of [Jenkins](#2-5-Jenkins)
5. select Multibranch Pipeline and then OK.
6. in Branch sources section select GitHub.
    - add credentials (GitHub token as a secret text)
    - insert URL of repository in HTTPS format
    - choose branch strategy
    - insert script path (default is Jenkinsfile)
7. click on the save button to see the pipeline dashboard.
8. click on Build Now menu
---
#### <p align="center"> [Top](#Pine-Framework) </p>