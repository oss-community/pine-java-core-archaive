docker build -t samanalishiri/jenkins:latest ./docker/jenkins/ --build-arg GITHUB_JENKINS_TOKEN=$GITHUB_JENKINS_TOKEN --no-cache
# docker push samanalishiri/jenkins:latest