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

echo 'step [sonar] begin'
mvn sonar:sonar -s settings.xml -P sonar
echo 'step [sonar] end'

echo 'step [install] begin'
mvn install -DskipTests=true -s settings.xml
echo 'step [install] end'

echo 'step [site] begin'
mvn site:site site:stage -s settings.xml -P site,javadoc,changelog,test-report,github
echo 'step [site] end'

echo 'step [publish site] begin'
mvn scm-publish:publish-scm -s settings.xml -P site,github
echo 'step [publish site] end'

echo 'step [jfrog artifactory] begin'
mvn deploy -s settings.xml -P jfrog
echo 'step [jfrog artifactory] end'

echo 'step [github artifactory] begin'
mvn deploy -s settings.xml -P github
echo 'step [github artifactory] end'

echo 'step [nexus artifactory] begin'
mvn deploy -s settings.xml -P nexus -DskipTests=true
echo 'step [nexus artifactory] end'
