pipeline {
  agent any
  stages {
    stage('log version') {
      parallel {
        stage('log version') {
          steps {
            bat 'java -version'
            bat 'mvn --version'
            bat 'git --version'
          }
        }

        stage('verify pom') {
          steps {
            fileExists 'pom.xml'
          }
        }

        stage('verify settings.xml') {
          steps {
            fileExists 'settings.xml'
          }
        }

      }
    }

    stage('build') {
      steps {
        bat 'mvn clean package -DskipTests=true -s settings.xml'
      }
    }

    stage('test') {
      steps {
        bat 'mvn test -s settings.xml'
      }
    }

    stage('check style') {
      parallel {
        stage('check style') {
          steps {
            bat 'mvn checkstyle:check -s settings.xml'
          }
        }

        stage('sonar') {
          steps {
            bat 'mvn sonar:sonar -s settings.xml'
          }
        }

      }
    }

    stage('site') {
      steps {
        bat 'mvn site:site site:stage -s settings.xml'
      }
    }

    stage('publish site') {
      steps {
        bat 'mvn scm-publish:publish-scm -s settings.xml'
      }
    }

    stage('deploy to JFrog artifactory') {
      steps {
        bat 'mvn deploy -s settings.xml'
      }
    }

  }
}