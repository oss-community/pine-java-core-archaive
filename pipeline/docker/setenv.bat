call setx /M GITHUB_JENKINS_TOKEN ???
call setx /M SONAR_DB sonar
call setx /M SONAR_DB_USER sonar
call setx /M SONAR_DB_PASSWORD sonar
call setx /M SONAR_JDBC_URL jdbc:postgresql://pine-sonarqube-db:5432/sonar

call setx /M JFROG_DB artifactory
call setx /M JFROG_DB_USER artifactory
call setx /M JFROG_DB_PASSWORD password
call setx /M JF_SHARED_DATABASE_TYPE postgresql
call setx /M JF_SHARED_DATABASE_DRIVER "org.postgresql.Driver"
call setx /M JF_SHARED_DATABASE_URL jdbc:postgresql://pine-jfrog-db:5432/artifactory
