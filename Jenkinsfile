pipeline {
  agent any
  tools {
      maven 'M2_HOME'
      jdk 'JAVA_HOME'
    }
  stages {
    stage('Logging and Verifying') {
      parallel {
        stage('Logging Version') {
          steps {
            sh 'java -version'
            sh 'mvn --version'
            sh 'git --version'
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
