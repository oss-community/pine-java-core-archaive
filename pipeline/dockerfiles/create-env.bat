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

echo CONCOURSE_DB=%CONCOURSE_DB% >> .\pipeline\docker\.env
echo CONCOURSE_DB_USER=%CONCOURSE_DB_USER% >> .\pipeline\docker\.env
echo CONCOURSE_DB_PASSWORD=%CONCOURSE_DB_PASSWORD% >> .\pipeline\docker\.env
echo CONCOURSE_JDBC_URL=%CONCOURSE_JDBC_URL% >> .\pipeline\docker\.env
echo CONCOURSE_EXTERNAL_URL=%CONCOURSE_EXTERNAL_URL% >> .\pipeline\docker\.env
echo CONCOURSE_CLUSTER_NAME=%CONCOURSE_CLUSTER_NAME% >> .\pipeline\docker\.env
echo CONCOURSE_ADD_LOCAL_USER=%CONCOURSE_ADD_LOCAL_USER% >> .\pipeline\docker\.env
echo CONCOURSE_ADD_LOCAL_PASSWORD=%CONCOURSE_ADD_LOCAL_PASSWORD% >> .\pipeline\docker\.env
echo CONCOURSE_MAIN_TEAM_LOCAL_USER=%CONCOURSE_MAIN_TEAM_LOCAL_USER% >> .\pipeline\docker\.env
echo CONCOURSE_CLIENT_SECRET=%CONCOURSE_CLIENT_SECRET% >> .\pipeline\docker\.env
echo CONCOURSE_TSA_CLIENT_SECRET=%CONCOURSE_TSA_CLIENT_SECRET% >> .\pipeline\docker\.env
