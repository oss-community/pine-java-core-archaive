pipeline {
  agent any

  stages {
    stage('Logging and Verifying') {
      parallel {
        stage('Logging Version') {
          steps {
            bat 'java -version'
            bat 'mvn --version'
            bat 'git --version'
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
        bat 'mvn clean package -DskipTests=true -s settings.xml'
      }
    }

    stage('Test') {
      steps {
        bat 'mvn test -s settings.xml'
      }
    }

    stage('Clean Code') {
      parallel {
        stage('Check Style') {
          steps {
            bat 'mvn checkstyle:check -s settings.xml'
          }
        }

        stage('Sonar') {
          steps {
            bat 'mvn sonar:sonar -s settings.xml'
          }
        }

      }
    }

    stage('Making Site') {
      steps {
        bat 'mvn site:site site:stage -s settings.xml'
      }
    }

    stage('Publishing Site') {
      steps {
        bat 'mvn scm-publish:publish-scm -s settings.xml'
      }
    }

    stage('Deploying to Artifactory') {
      steps {
        bat 'mvn deploy -s settings.xml'
      }
    }

  }
}
