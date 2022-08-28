# localhost, build docker
call setx /M GITHUB_JENKINS_TOKEN ???

# localhost
call setx /M SONAR_TOKEN 113f5c14c05348d5850924c54819a4bb5616bbc9
call setx /M SONAR_URL http://localhost:9000
# build docker
call setx /M SONAR_DB sonar
call setx /M SONAR_DB_USER sonar
call setx /M SONAR_DB_PASSWORD sonar
call setx /M SONAR_JDBC_URL jdbc:postgresql://pine-sonarqube-db:5432/sonar

# localhost
call setx /M JFROG_ARTIFACTORY_USERNAME admin
call setx /M JFROG_ARTIFACTORY_PASSWORD AP7WEXNhxLUqHL3vJT5iAxHAuNF
call setx /M JFROG_ARTIFACTORY_SNAPSHOT_URL http://localhost:8081/artifactory/pine-libs-snapshot
call setx /M JFROG_ARTIFACTORY_CONTEXT_URL http://localhost:8082/artifactory
call setx /M JFROG_ARTIFACTORY_REPOSITORY_PREFIX pine
# build docker
call setx /M JFROG_DB artifactory
call setx /M JFROG_DB_USER artifactory
call setx /M JFROG_DB_PASSWORD password
call setx /M JF_SHARED_DATABASE_TYPE postgresql
call setx /M JF_SHARED_DATABASE_DRIVER "org.postgresql.Driver"
call setx /M JF_SHARED_DATABASE_URL jdbc:postgresql://pine-jfrog-db:5432/artifactory

# build docker
call setx /M CONCOURSE_DB concourse
call setx /M CONCOURSE_DB_USER concourse_user
call setx /M CONCOURSE_DB_PASSWORD concourse_pass
call setx /M CONCOURSE_JDBC_URL jdbc:postgresql://pine-concourse-db:5432/sonar
call setx /M CONCOURSE_EXTERNAL_URL http://pine-concourse:8083
call setx /M CONCOURSE_CLUSTER_NAME pine
call setx /M CONCOURSE_ADD_LOCAL_USER pine
call setx /M CONCOURSE_ADD_LOCAL_PASSWORD pine
call setx /M CONCOURSE_MAIN_TEAM_LOCAL_USER pine
call setx /M CONCOURSE_CLIENT_SECRET Y29uY291cnNlLXdlYgo=
call setx /M CONCOURSE_TSA_CLIENT_SECRET Y29uY291cnNlLXdvcmtlcgo=



