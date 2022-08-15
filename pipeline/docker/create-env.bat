echo GITHUB_JENKINS_TOKEN=%GITHUB_JENKINS_TOKEN% > .\pipeline\docker\.env

echo SONAR_DB=%SONAR_DB% >> .\pipeline\docker\.env
echo SONAR_DB_USER=%SONAR_DB_USER% >> .\pipeline\docker\.env
echo SONAR_DB_PASSWORD=%SONAR_DB_PASSWORD% >> .\pipeline\docker\.env
echo SONAR_JDBC_URL=%SONAR_JDBC_URL% >> .\pipeline\docker\.env

echo JFROG_DB=%JFROG_DB% >> .\pipeline\docker\.env
echo JFROG_DB_USER=%JFROG_DB_USER% >> .\pipeline\docker\.env
echo JFROG_DB_PASSWORD=%JFROG_DB_PASSWORD% >> .\pipeline\docker\.env
echo JF_SHARED_DATABASE_TYPE=%JF_SHARED_DATABASE_TYPE% >> .\pipeline\docker\.env
echo JF_SHARED_DATABASE_DRIVER=%JF_SHARED_DATABASE_DRIVER% >> .\pipeline\docker\.env
echo JF_SHARED_DATABASE_URL=%JF_SHARED_DATABASE_URL% >> .\pipeline\docker\.env
