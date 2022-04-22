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
    - **[2-4 SonarQube](#2-4-Sonarqube)**
        - **[2-4-1 Sonarqube Server](#2-4-1-Sonarqube-Server)**
        - **[2-4-2 Sonar Scanner](#2-4-2-Sonar-Scanner)**
    - **[2-5 Jenkins](#2-5-Jenkins)**
    - **[2-6 JFrog](#2-6-JFrog)**
    - **[2-7 IDE Setting](#2-7-IDE-Setting)**
        - **[2-7-1 Intellij IDEA](#2-7-1-Intellij-IDEA)**
- **[3 Pipeline](#3-Pipeline)**
- **[4 Build](#4-Build)**
- **[5 Install](#5-Install)**

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
sed -i 's/$PATH/$JAVA_HOME\/bin:$PATH/g' .bashrc 
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
sed -i 's/$PATH/M2_HOME\/bin:$PATH/g' .bashrc 
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

## <span style="color: RoyalBlue">2-4 Sonarqube</span>

### 2-4-1 Sonarqube Server

1. [download sonarqube](https://www.sonarqube.org/downloads/) then extract it
2. in the extracted path execute the following command
    - Linux: `bin/linux-x86-64/sonar.sh start`
    - Windows: `bin/windows-x86-64/StartSonar.bat`
3. browse SonarQube for localhost installation at `http://localhost:9000`.
    - username: admin
    - password: admin

Generate token at _**administration > security > users > Tokens**_ and add environment variable.

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
sed -i 's/$PATH/$SONAR_SCANNER_HOME\/bin:$PATH/g' .bashrc
source ~/.bashrc
```
## 2-5 Jenkins

1. [download jenkins as war file](https://www.jenkins.io/download/)
2. `java -jar jenkins.war --httpPort=9090`
3. browse Jenkins for localhost installation at http://localhost:9090.
    - username: admin
    - password: look at the console

## 2-6 JFrog

1. [download Jfrog](https://jfrog.com/download-jfrog-platform/) and extract it
2. in the extracted path execute the following command
    - Linux: `$JFROG_HOME/artifactory/app/bin/artifactoryctl`
    - Windows: `$JFROG_HOME\app\bin\artifactory.bat`
3. browse JFrog for localhost installation at `http://localhost:8082/ui`.
    - username: admin
    - password: password

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

## 2-7 IDE Setting

### 2-7-1 Intellij IDEA

#### Checkstyle

1. [install google check style plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/versions)
2. add customized checkstyle.xml to _**setting/preferences > Tools > Checkstyle > Configuration File**_

<p align="center"><img height="300" width="300" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/idea-checkstyle-001.png"/></p>

3. import customized checkstyle.xml to _**setting/preferences > Editor > Code Style > Schema**_

<p align="center"><img height="300" width="300" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/idea-checkstyle-002.png"/></p>

#### Test Coverage

1. click on _**Navigate > Search Everywhere**_, then type `Registry...`.
2. in registry, type `idea.coverage` then disable
    - idea.coverage.new.sampling.enable
    - idea.coverage.test.tracking.enable
    - idea.coverage.tracing.enable

---

# <span style="color: Crimson">3 Pipeline</span>

1. click on New Item menu in dashboard of [Jenkins](#2-5-Jenkins)
<p align="center"><img height="300" width="700" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/pipeline-1.png"/></p>


2. select Pipeline and then OK.
<p align="center"><img height="300" width="700" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/pipeline-2.png"/></p>


3. in the configuration page check GitHub project item and insert the url of the project.
<p align="center"><img height="300" width="700" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/pipeline-3.png"/></p>


4. in Advanced Project Options section select Pipeline script from SCM as pipeline definition.
   - select Git as a SCM
   - insert repository url
   - add credentials
   - choose branch
   - insert script path (default is Jenkinsfile)
<p align="center"><img height="300" width="700" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/pipeline-4.png"/></p>

5. click on the save button to see the pipeline dashboard.
<p align="center"><img height="300" width="700" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/pipeline-4-1.png"/></p>


6. click on Build Now menu
<p align="center"><img height="300" width="700" src="https://github.com/saman-oss/pine-java-core/blob/main/docs/pipeline-5.png"/></p>

---

# <span style="color: Crimson">4 Build</span>

``` bash
  mvn clean package -DskipTests=true -s settings.xml
  mvn test -s settings.xml
  mvn checkstyle:check -s settings.xml
  mvn site:site site:stage -s settings.xml
  mvn scm-publish:publish-scm -s settings.xml
  mvn sonar:sonar -s settings.xml
  mvn deploy -s settings.xml
  ```

---

# <span style="color: Crimson">5 Install</span>

``` bash
mvn clean install -DskipTests=true
```

#### <p align="center"> [Top](#Pine-Framework) </p>
