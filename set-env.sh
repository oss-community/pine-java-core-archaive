echo "export GITHUB_USERNAME=samanalishiri" >> ${HOME}/.bashrc
echo "export GITHUB_EMAIL=samanalishiri@gmail.com" >> ${HOME}/.bashrc
echo "export GITHUB_JENKINS_TOKEN=not_generated" >> ${HOME}/.bashrc
echo "export GITHUB_PACKAGE_TOKEN=not_generated" >> ${HOME}/.bashrc
echo "export GITHUB_ARTIFACTORY_URL=saman-oss/pine-java-core" >> ${HOME}/.bashrc

echo "export SONAR_TOKEN=not_generated" >> ${HOME}/.bashrc
echo "export SONAR_URL=pine-sonarqube" >> ${HOME}/.bashrc
echo "export SONAR_DB=sonar" >> ${HOME}/.bashrc
echo "export SONAR_DB USER=sonar_user" >> ${HOME}/.bashrc
echo "export SONAR_DB_PASSWORD=sonar_pass" >> ${HOME}/.bashrc
echo "export SONAR_JDBC_URL=jdbc:postgresql://pine-sonarqube-db:5432/sonar" >> ${HOME}/.bashrc

echo "export JFROG_ARTIFACTORY_USERNAME=admin" >> ${HOME}/.bashrc
echo "export JFROG_ARTIFACTORY_ENCRYPTED_PASSWORD=not_generated" >> ${HOME}/.bashrc
echo "export JFROG_ARTIFACTORY_SNAPSHOT_URL=http://pine-jfrog:8081/artifactory/pine-libs-snapshot" >> ${HOME}/.bashrc
echo "export JFROG_ARTIFACTORY_CONTEXT_URL=http://pine-jfrog:8082/artifactory" >> ${HOME}/.bashrc
echo "export JFROG_ARTIFACTORY_REPOSITORY_PREFIX=pine" >> ${HOME}/.bashrc
echo "export JFROG_DB=artifactory" >> ${HOME}/.bashrc
echo "export JFROG_DB_USER=artifactory_user" >> ${HOME}/.bashrc
echo "export JFROG_DB_PASSWORD=artifactory_pass" >> ${HOME}/.bashrc
echo "export JF_SHARED_DATABASE_TYPE=postgresql" >> ${HOME}/.bashrc
echo "export JF_SHARED_DATABASE_DRIVER="org.postgresql.Driver"" >> ${HOME}/.bashrc
echo "export JF_SHARED_DATABASE_URL=jdbc:postgresql://pine-jfrog-db:5432/artifactory" >> ${HOME}/.bashrc

echo "export NEXUS_ARTIFACTORY_USERNAME=admin" >> ${HOME}/.bashrc
echo "export NEXUS_ARTIFACTORY_ENCRYPTED_PASSWORD=not_generated" >> ${HOME}/.bashrc
echo "export NEXUS_ARTIFACTORY_SNAPSHOT_URL=http://pine-nexus:8084/repository/maven-snapshots" >> ${HOME}/.bashrc

export "CONCOURSE_DB=concourse" >> ${HOME}/.bashrc
export "CONCOURSE_DB_HOST=pine-concourse-db" >> ${HOME}/.bashrc
export "CONCOURSE_DB_PORT=5432" >> ${HOME}/.bashrc
export "CONCOURSE_DB_USER=concourse_user" >> ${HOME}/.bashrc
export "CONCOURSE_DB_PASSWORD=concourse_pass" >> ${HOME}/.bashrc
export "CONCOURSE_EXTERNAL_URL=http://localhost:8083" >> ${HOME}/.bashrc
export "CONCOURSE_CLUSTER_NAME=pineframework" >> ${HOME}/.bashrc
export "CONCOURSE_ADD_LOCAL_USER=pine" >> ${HOME}/.bashrc
export "CONCOURSE_ADD_LOCAL_PASSWORD=pine" >> ${HOME}/.bashrc
export "CONCOURSE_MAIN_TEAM_LOCAL_USER=pine" >> ${HOME}/.bashrc
export "CONCOURSE_TSA_HOST=pine-concourse-web:2222" >> ${HOME}/.bashrc

source ${HOME}/.bashrc