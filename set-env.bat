call setx /M GITHUB_USERNAME samanalishiri
call setx /M GITHUB_EMAIL samanalishiri@gmail.com
call setx /M GITHUB_JENKINS_TOKEN not_generated
call setx /M GITHUB_PACKAGE_TOKEN not_generated
call setx /M GITHUB_ARTIFACTORY_URL saman-oss/pine-java-core

call setx /M SONAR_TOKEN not_generated
call setx /M SONAR_URL http://pine-sonarqube:9000
call setx /M SONAR_DB sonar
call setx /M SONAR_DB_USER sonar_user
call setx /M SONAR_DB_PASSWORD sonar_pass
call setx /M SONAR_JDBC_URL jdbc:postgresql://pine-sonarqube-db:5432/sonar

call setx /M JFROG_ARTIFACTORY_USERNAME admin
call setx /M JFROG_ARTIFACTORY_ENCRYPTED_PASSWORD not_generated
call setx /M JFROG_ARTIFACTORY_SNAPSHOT_URL http://pine-jfrog:8081/artifactory/pine-libs-snapshot
call setx /M JFROG_ARTIFACTORY_RELEASE_URL http://pine-jfrog:8081/artifactory/pine-libs-release
call setx /M JFROG_ARTIFACTORY_CONTEXT_URL http://pine-jfrog:8082/artifactory
call setx /M JFROG_ARTIFACTORY_REPOSITORY_PREFIX pine
call setx /M JFROG_DB artifactory
call setx /M JFROG_DB_USER artifactory_user
call setx /M JFROG_DB_PASSWORD artifactory_pass
call setx /M JF_SHARED_DATABASE_TYPE postgresql
call setx /M JF_SHARED_DATABASE_DRIVER "org.postgresql.Driver"
call setx /M JF_SHARED_DATABASE_URL jdbc:postgresql://pine-jfrog-db:5432/artifactory

call setx /M NEXUS_ARTIFACTORY_USERNAME admin
call setx /M NEXUS_ARTIFACTORY_PASSWORD not_generated
call setx /M NEXUS_ARTIFACTORY_HOST_URL http://pine-nexus:8084/
call setx /M NEXUS_ARTIFACTORY_SNAPSHOT_URL http://pine-nexus:8084/repository/pine-maven-snapshots/
call setx /M NEXUS_ARTIFACTORY_RELEASE_URL http://pine-nexus:8084/repository/pine-maven-releases/

call setx /M CONCOURSE_DB concourse
call setx /M CONCOURSE_DB_HOST pine-concourse-db
call setx /M CONCOURSE_DB_PORT 5432
call setx /M CONCOURSE_DB_USER concourse_user
call setx /M CONCOURSE_DB_PASSWORD concourse_pass
call setx /M CONCOURSE_EXTERNAL_URL http://localhost:8083
call setx /M CONCOURSE_CLUSTER_NAME pineframework
call setx /M CONCOURSE_ADD_LOCAL_USER pine
call setx /M CONCOURSE_ADD_LOCAL_PASSWORD pine
call setx /M CONCOURSE_MAIN_TEAM_LOCAL_USER pine
call setx /M CONCOURSE_TSA_HOST pine-concourse-web:2222



