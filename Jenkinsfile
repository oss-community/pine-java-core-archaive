pipeline {
  agent any
  stages {
    stage('log version') {
      parallel {
        stage('log version') {
          steps {
            sh 'java -version'
          }
        }

        stage('verify pom') {
          steps {
            fileExists 'pom.xml'
          }
        }

        stage('error') {
          steps {
            fileExists 'settings.xml'
          }
        }

      }
    }

    stage('build') {
      steps {
        sh 'mvn clean package -DskipTests=true -s settings.xml'
      }
    }

    stage('test') {
      steps {
        sh 'mvn test -s settings.xml'
      }
    }

    stage('check style') {
      parallel {
        stage('check style') {
          steps {
            sh 'mvn checkstyle:check -s settings.xml'
          }
        }

        stage('sonar') {
          steps {
            sh 'mvn sonar:sonar -s settings.xml'
          }
        }

      }
    }

    stage('site') {
      steps {
        sh 'mvn site:site site:stage -s settings.xml'
      }
    }

    stage('publish site') {
      steps {
        sh 'mvn scm-publish:publish-scm -s settings.xml'
      }
    }

    stage('deploy to JFrog artifactory') {
      steps {
        sh 'mvn deploy -s settings.xml'
      }
    }

  }
}