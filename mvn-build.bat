cls
call mvn clean package checkstyle:check site:site site:stage scm-publish:publish-scm
call mvn sonar:sonar