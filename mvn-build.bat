cls
call mvn clean package -DskipTests=true -s settings.xml
call mvn test -s settings.xml
call mvn checkstyle:check -s settings.xml
call mvn site:site site:stage -s settings.xml
call mvn scm-publish:publish-scm -s settings.xml
call mvn sonar:sonar -s settings.xml
call mvn deploy -s settings.xml