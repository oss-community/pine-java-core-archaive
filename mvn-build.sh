clear
echo 'step [validate] begin'
mvn validate
echo 'step [validate] end'

echo 'step [build] begin'
mvn clean package -DskipTests=true -s settings.xml -P source,javadoc,license
echo 'step [build] end'

echo 'step [test] begin'
mvn test -s settings.xml
echo 'step [test] end'

echo 'step [checkstyle] begin'
mvn checkstyle:check -s settings.xml -P checkstyle
echo 'step [checkstyle] end'

echo 'step [install] begin'
mvn install -DskipTests=true -s settings.xml
echo 'step [install] end'

echo 'step [site] begin'
mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report,github
call mvn site:run -s settings.xml -P site
echo 'step [site] end'