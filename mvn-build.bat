cls
call echo 'step [build] begin'
call mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license
call echo 'step [build] end'

call echo 'step [test] begin'
call mvn test -s settings.xml
call echo 'step [test] end'

call echo 'step [checkstyle] begin'
call mvn checkstyle:check -s settings.xml -P checkstyle
call echo 'step [checkstyle] end'

call echo 'step [install] begin'
call mvn install -DskipTests=true -s settings.xml
call echo 'step [install] end'

call echo 'step [site] begin'
call mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report
call echo 'step [site] end'

call echo 'step [publish site] begin'
call mvn scm-publish:publish-scm -s settings.xml -P publish
call echo 'step [publish site] end'

call echo 'step [sonar] begin'
#call mvn sonar:sonar -s settings.xml -P sonar
call echo 'step [sonar] end'

call echo 'step [artifactory] begin'
#call mvn deploy -s settings.xml -p artifactory
call echo 'step [artifactory] end'