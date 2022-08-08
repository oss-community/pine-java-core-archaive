call setx /M GITHUB_JENKINS_TOKEN ???

call setx /M SONAR_TOKEN ???
call setx /M SONAR_URL http://localhost:9000

call setx /M JFROG_ARTIFACTORY_USERNAME admin
call setx /M JFROG_ARTIFACTORY_PASSWORD ???
call setx /M JFROG_ARTIFACTORY_SNAPSHOT_URL http://localhost:8081/artifactory/pine-libs-snapshot
call setx /M JFROG_ARTIFACTORY_CONTEXT_URL http://localhost:8082/artifactory
call setx /M JFROG_ARTIFACTORY_REPOSITORY_PREFIX pine