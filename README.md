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
- **[Prerequisite](#Prerequisite)**
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
    - **[Docker](#Docker)**
    - **[Podman](#Podman)**
    - **[Concourse](#Concourse)**
- **[Build](#Build-and-Test)**
- **[Install](#Install)**
- **[Pipeline](#Pipeline)**
    - **[Setup Pipeline Tools](#Setup-Pipeline-Tools)**
    - **[Maven Pipeline](#Maven-Pipeline)**
    - **[Jenkins Pipeline](#Jenkins-Pipeline)**
    - **[Concourse Pipeline](#Concourse-Pipeline)**

---

# <p align="center"><span style="color: Crimson">Pine Java Core</span></p>

Pine Java Core is a basic module is included utils, helpers, abstracts and the other basic facilities.<br/>
Pine Core Java has modules as follows.<br/>

- helper
- document
- i18n

## <span style="color: Crimson">Prerequisite</span>

- Java
- Maven
- Git
- Docker/Podman (Only for pipeline)

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

[Generate a token by GitHub.com](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
then create a text file named token.txt and copy the token from GitHub and paste in the `token.txt`.
after that login via GitHub CLI.

```shell
   gh auth login -p ssh -h github.com --with-token < token.txt
   gh repo list
```

Generate ssh keys by git tool (select one solution).

```shell
  ssh-keygen -t rsa -C "comment" #solution 1: interactive mode
  ssh-keygen -t rsa -C "comment" -N '' -f ~/.ssh/id_rsa #solution 2: without prompt
  add key: ssh-add ~/.ssh/id_rsa
```

Deploy public keys via GitHub CLI (select one solution).

```shell
  gh repo deploy-key add ~/.ssh/id_rsa.pub -R owner/repository-name -t key-title -w #solution 1: deploy key into repository
  gh ssh-key add ~/.ssh/id_rsa.pub #solution 2: deploy key into user
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

Browse SonarQube for localhost installation at http://localhost:9000.

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

Browse Jenkins for localhost installation at http://localhost:8080.

- username: admin
- password: look at the console

### <span style="color: RoyalBlue">JFrog</span>

Download [Jfrog](https://jfrog.com/download-jfrog-platform/) and extract it.<br/>
In the extracted path execute the following command.<br/>

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

Change password at _**Administration > User Management > Users**_.<br/>
Get encrypted password from _**Edit Profile**_ menu.<br/>
Create repository by _**Quick Setup**_ menu.<br/>

### <span style="color: RoyalBlue">IDE Setting</span>

#### Intellij IDEA

##### Checkstyle

Install [google check style](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/versions) plugin.<br/>
Add customized checkstyle.xml to _**setting/preferences > Tools > Checkstyle > Configuration File**_.<br/>
Import customized checkstyle.xml to _**setting/preferences > Editor > Code Style > Schema**_.<br/>

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

### <span style="color: RoyalBlue">ِDocker</span>

Install [Docker Desktop](https://docs.docker.com/get-docker/)

### <span style="color: RoyalBlue">Podman</span>

Install [Podman](https://podman.io/getting-started/installation)<br/>
Install [Podman Desktop](https://podman-desktop.io/)<br/>
Install [Podman compose](https://github.com/containers/podman-compose)<br/>

For installation of Podman Compose, it needs to install python compiler.

### <span style="color: RoyalBlue">Concourse</span>

At first define environment variables in .env file.<br/>

```dotenv
CONCOURSE_DB=concourse 
CONCOURSE_DB_HOST=concourse-db 
CONCOURSE_DB_PORT=5432 
CONCOURSE_DB_USER=concourse_user 
CONCOURSE_DB_PASSWORD=concourse_pass 
CONCOURSE_EXTERNAL_URL=http://localhost:8088 
CONCOURSE_CLUSTER_NAME=pineframework 
CONCOURSE_ADD_LOCAL_USER=pine 
CONCOURSE_ADD_LOCAL_PASSWORD=pine 
CONCOURSE_MAIN_TEAM_LOCAL_USER=pine 
CONCOURSE_TSA_HOST=concourse-web:2222 
```

Then, the certificates must be generated and move them to a directory in order to use that as a directory mapping.<br/>
For windows

```shell
mkdir %HOMEPATH%\pine\keys
call ssh-keygen -t rsa -b 4096 -m PEM -f %HOMEPATH%\pine\keys\session_signing_key
call ssh-keygen -t rsa -b 4096 -m PEM -f %HOMEPATH%\pine\keys\tsa_host_key
call ssh-keygen -t rsa -b 4096 -m PEM -f %HOMEPATH%\pine\keys\worker_key

ren %HOMEPATH%\pine\keys\worker_key.pub authorized_worker_keys
copy %HOMEPATH%\pine\keys\* %HOMEPATH%\docker_compose\concourse\keys
```

For Linux

```shell
mkdir ~/pine/keys

ssh-keygen -t rsa -b 4096 -m PEM -f ~/pine/keys/session_signing_key
ssh-keygen -t rsa -b 4096 -m PEM -f ~/pine/keys/tsa_host_key
ssh-keygen -t rsa -b 4096 -m PEM -f ~/pine/keys/worker_key

mv ~/pine/keys/worker_key.pub ~/pine/keys/authorized_worker_keys
cp ~/pine/keys/* ~/docker_compose/concourse/keys
```

After that create the following docker-compose.yaml file to execute by Docker/Podman compose.

```yaml
  concourse-db:
    container_name: concourse-db
    hostname: concourse-db
    restart: always
    image: postgres
    environment:
      POSTGRES_DB: ${CONCOURSE_DB}
      POSTGRES_PASSWORD: ${CONCOURSE_DB_PASSWORD}
      POSTGRES_USER: ${CONCOURSE_DB_USER}
      PGDATA: /database
    ports:
      - "5053:5432"
    volumes:
      - ~/docker_compose/concourse-postgresql:/var/lib/postgresql
    ulimits:
      nproc: 262144
      nofile:
        soft: 32000
        hard: 40000
  concourse-web:
    image: concourse/concourse
    container_name: concourse-web
    hostname: concourse-web
    restart: always
    command: web
    privileged: true
    depends_on: [ concourse-db ]
    ports: [ "8083:8080" ]
    volumes:
      - ~/docker_compose/concourse/keys:/keys
    environment:
      CONCOURSE_POSTGRES_HOST: ${CONCOURSE_DB_HOST}
      CONCOURSE_POSTGRES_PORT: ${CONCOURSE_DB_PORT}
      CONCOURSE_POSTGRES_DATABASE: ${CONCOURSE_DB}
      CONCOURSE_POSTGRES_USER: ${CONCOURSE_DB_USER}
      CONCOURSE_POSTGRES_PASSWORD: ${CONCOURSE_DB_PASSWORD}
      CONCOURSE_ADD_LOCAL_USER: ${CONCOURSE_ADD_LOCAL_USER}:${CONCOURSE_ADD_LOCAL_PASSWORD}
      CONCOURSE_MAIN_TEAM_LOCAL_USER: ${CONCOURSE_MAIN_TEAM_LOCAL_USER}
      CONCOURSE_EXTERNAL_URL: ${CONCOURSE_EXTERNAL_URL}
      CONCOURSE_CLUSTER_NAME: ${CONCOURSE_CLUSTER_NAME}
      CONCOURSE_SESSION_SIGNING_KEY: /keys/session_signing_key
      CONCOURSE_TSA_HOST_KEY: /keys/tsa_host_key
      CONCOURSE_TSA_AUTHORIZED_KEYS: ./keys/authorized_worker_keys
  concourse-worker:
    image: concourse/concourse
    container_name: concourse-worker
    hostname: concourse-worker
    restart: always
    command: worker
    privileged: true
    depends_on: [ concourse-web ]
    volumes:
      - ~/docker_compose/concourse/keys:/keys
    environment:
      CONCOURSE_WORK_DIR: /var/lib/concourse
      CONCOURSE_TSA_HOST: ${CONCOURSE_TSA_HOST}
      CONCOURSE_TSA_PUBLIC_KEY: /keys/tsa_host_key.pub
      CONCOURSE_TSA_WORKER_PRIVATE_KEY: /keys/worker_key
      CONCOURSE_CONTAINERD_DNS_PROXY_ENABLE: "true"
      CONCOURSE_CONTAINERD_DNS_SERVER: "1.1.1.1,8.8.8.8"
      CONCOURSE_RUNTIME: "containerd"
      CONCOURSE_BAGGAGECLAIM_DRIVER: "naive"
```

Finally, deploy the services to Docker/Podman.

```shell
docker compose --file docker-compose.yaml --project-name concourse --env-file .env up --build -d
```

---

## <span style="color: Crimson">Build and Test</span>

``` shell
mvn validate
mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license
mvn test -s settings.xml
mvn checkstyle:check -s settings.xml -P checkstyle
mvn install -DskipTests=true -s settings.xml
mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report
```

### <span style="color: RoyalBlue">Site</span>

View maven site on [GitHub page](https://saman-oss.github.io/pine-java-core/) of project.

``` shell
mvn scm-publish:publish-scm -s settings.xml -P publish
```

Also, you can see maven site [locally](http://localhost:8000/).

``` shell
mvn site:run -s settings.xml -P site
```

---

## <span style="color: Crimson">Install</span>

``` shell
mvn validate
mvn clean install -DskipTests=true
```

---

## <span style="color: Crimson">Pipeline</span>

It should be executed `pipeline-install` file that located in root path of project .

##### Windows

If docker and podman installed on the machine, then make sure podman is stopped and docker is running. And make sure
docker installation path was added to system path.

```shell
podman machine stop
bcdedit /set hypervisorlaunchtype auto
DockerCli -SwitchDaemon
```

Then execute the following bat file.

```shell
.\pipeline-install.bat
```

##### Linux

```shell
./pipeline-install
```

### Setup Pipeline Tools

Go to the Sonar console http://localhost:9000 and follow the instruction [SonarQube](#SonarQube) to generate token.<br/>
Go to the JFrog console http://localhost:8082/ui and follow the instruction [JFrog](#JFrog) to get encrypted
password.<br/>
Go to the Jenkins console http://localhost:8080 and add the following secret variables to invoke by `credentials()`
.<br/>

- github_username
- github_email
- github_jenkins_token
- github_package_token
- github_artifactory_url
- sonar_token
- sonar_url
- jfrog_artifactory_username
- jfrog_artifactory_encrypted_password
- jfrog_artifactory_snapshot_url
- jfrog_artifactory_context_url
- jfrog_artifactory_repository_prefix

<p align="justify">
Go to the Concourse console http://localhost:8080 then login via `pine` as user and `pine` as password, after that 
download CLI tools and add it to system path. Add private key named `id_rsa` and public key named `id_rsa.pub` that 
already generated in `user-home/pine/keys` by `ssh-keygen` to`credentials.yml` file locate in `ci/concourse` folder then
execute  the following command. Also deploy `id_rsa.pub` to the repository.
</p>

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
2. add secret variables to invoke by `credentials()`.
3. click on New Item menu in dashboard of [Jenkins](#Jenkins)
4. select Pipeline and then OK.
5. in the configuration page check `GitHub hook trigger for GITScm polling` item and insert the url of the project.
6. in Advanced Project Options section, select `Pipeline script from SCM` as pipeline definition.
    - select Git as an SCM
    - insert URL of repository in HTTPS format
    - add credentials (GitHub token as a secret text)
    - choose branch
    - insert script path (default is Jenkinsfile) and in this case is in the ci/jenkins directory
7. click on the save button to see the pipeline dashboard.
8. click on Build Now menu

### <span style="color: RoyalBlue">Concourse Pipeline</span>

```shell
fly --target pine login --team-name main --concourse-url http://localhost:8083 -u pine -p pine
fly --target pine set-pipeline --pipeline pine --config .\ci\concourse\pipeline.yml --load-vars-from .\ci\concourse\credentials.yml
fly -t pine unpause-pipeline -p pine
```

---

#### <p align="center"> [Top](#Pine-Framework) </p>