cls
call echo 'step [validate] begin'
call mvn validate
call echo 'step [validate] end'

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
call mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report,github
call mvn site:run -s settings.xml -P site
call echo 'step [site] end'