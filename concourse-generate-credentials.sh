echo # credentials > ./ci/concourse/credentials.yml
echo github-user-name: $GITHUB_USERNAME >> ./ci/concourse/credentials.yml
echo github-user-email: $GITHUB_EMAIL >> ./ci/concourse/credentials.yml
echo github-package-token: $GITHUB_PACKAGE_TOKEN >> ./ci/concourse/credentials.yml
echo github-artifactory-url: $GITHUB_ARTIFACTORY_URL >> ./ci/concourse/credentials.yml

echo jfrog-artifactory-username: $JFROG_ARTIFACTORY_USERNAME >> ./ci/concourse/credentials.yml
echo jfrog-artifactory-encrypted-password: $JFROG_ARTIFACTORY_ENCRYPTED_PASSWORD >> ./ci/concourse/credentials.yml
echo jfrog-artifactory-snapshot-url: $JFROG_ARTIFACTORY_SNAPSHOT_UR >> ./ci/concourse/credentials.yml
echo jfrog-artifactory-release-url: $JFROG_ARTIFACTORY_RELEASE_UR >> ./ci/concourse/credentials.yml
echo jfrog-artifactory-context-url: $JFROG_ARTIFACTORY_CONTEXT_URL >> ./ci/concourse/credentials.yml
echo jfrog-artifactory-repository-prefix: $JFROG_ARTIFACTORY_REPOSITORY_PREFIX >> ./ci/concourse/credentials.yml

echo nexus-artifactory-username: $NEXUS_ARTIFACTORY_USERNAME >> ./ci/concourse/credentials.yml
echo nexus-artifactory-password: $NEXUS_ARTIFACTORY_PASSWORD >> ./ci/concourse/credentials.yml
echo nexus-artifactory-host-url: $NEXUS_ARTIFACTORY_HOST_URL >> ./ci/concourse/credentials.yml
echo nexus-artifactory-snapshot-url: $NEXUS_ARTIFACTORY_SNAPSHOT_URL >> ./ci/concourse/credentials.yml
echo nexus-artifactory-release-url: $NEXUS_ARTIFACTORY_RELEASE_URL >> ./ci/concourse/credentials.yml

echo sonar-token: $SONAR_TOKEN >> ./ci/concourse/credentials.yml
echo sonar-url: $SONAR_URL >> ./ci/concourse/credentials.yml

echo github-key-pub: >> ./ci/concourse/credentials.yml
echo github-key: '|' >> ./ci/concourse/credentials.yml

mkdir ~/pine/keys ~/docker_compose

ssh-keygen -t rsa -C "concourse_team" -f ~/pine/keys/pipeline_pine
gh repo deploy-key add %HOMEPATH%/pine/keys/pipeline_pine.pub -R %GITHUB_ARTIFACTORY_URL% -t concourse_team-key-pub -w

ssh-keygen -t rsa -b 4096 -m PEM -f ~/pine/keys/session_signing_key
ssh-keygen -t rsa -b 4096 -m PEM -f ~/pine/keys/tsa_host_key
ssh-keygen -t rsa -b 4096 -m PEM -f ~/pine/keys/worker_key

mv ~/pine/keys/worker_key.pub ~/pine/keys/authorized_worker_keys
cp ~/pine/keys/* ~/docker_compose/concourse/keys

