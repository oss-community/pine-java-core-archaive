pipeline {
  agent any
  tools {
      maven 'maven3'
      jdk 'jdk17'
      git 'default-git'
    }
    environment {
       SONAR_TOKEN=credentials('sonar_token')
       SONAR_URL='http://localhost:9000'
       ARTIFACTORY_USERNAME='admin'
       ARTIFACTORY_PASSWORD=credentials('artifactory_password')
       ARTIFACTORY_SNAPSHOT_URL='http://localhost:8081/artifactory/pine-libs-snapshot'
       ARTIFACTORY_CONTEXT_URL='http://localhost:8082/artifactory'
       ARTIFACTORY_REPOSITORY_PREFIX='pine'
       JAVA_HOME='/var/jenkins_home/jdk'
       M2_HOME='/var/jenkins_home/maven'
       GIT_TERMINAL_PROMPT='1'
      }
  stages {
    stage('Verify and config') {
      parallel {
        stage('Logging Version') {
          steps {
            sh 'java -version'
            sh 'mvn --version'
            sh 'git --version'
            sh 'git config --global user.email samanalishiri@gmail.com'
            sh 'git config --global user.name samanalishiri'
            sh 'git remote -v'
          }
        }

        stage('Verifying POM') {
          steps {
            fileExists 'pom.xml'
          }
        }

        stage('Verifying Maven Settings') {
          steps {
            fileExists 'settings.xml'
          }
        }

      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test -s settings.xml'
      }
    }

    stage('Clean Code') {
      parallel {
        stage('Check Style') {
          steps {
            sh 'mvn checkstyle:check -s settings.xml -P checkstyle'
          }
        }

        stage('Sonar') {
          steps {
            sh 'mvn sonar:sonar -s settings.xml -P sonar'
          }
        }

      }
    }

    stage('Making Site') {
      steps {
        sh 'mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report'
      }
    }

    stage('Publishing Site') {
      steps {
        sh 'mvn scm-publish:publish-scm -s settings.xml -P publish'
      }
    }

    stage('Deploying to Artifactory') {
      steps {
        sh 'mvn deploy -s settings.xml -P artifactory'
      }
    }
  }
}
