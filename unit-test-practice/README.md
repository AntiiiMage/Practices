# Java Unit Report, jUnit + Jacoco Maven Snippets

* A short demonstration how to set-up junit and surefire ***xml html*** report in a mavenized Java environment.

* How to get code coverage by Jacoco

***Defects (To Do)***

* Maven build will succeed with test failures
* To generate all reports and code coverage then failes the build

##Add dependencies:
```xml
<dependencies>
	<dependency>
		<groupId>org.sonarsource.java</groupId>
		<artifactId>sonar-jacoco-listeners</artifactId>
		<version>3.8</version>
		<scope>test</scope>
	</dependency>
</dependencies>
```
##Configure Plugin
###Configurations of Surefire plugin:
```xml
...
<configuration>
	<skip>false</skip>
	<failIfNoTests>true</failIfNoTests>
	<reportsDirectory>${project.build.directory}/surefire-reports/antiiimageTest</reportsDirectory>
	<includes>
		<include>**/antiiimage/**</include>
	</includes>
	<testFailureIgnore>false</testFailureIgnore>
	<properties>
		<property>
			<name>listener</name>
			<value>org.sonar.java.jacoco.JUnitListener</value>
		</property>
	</properties>
</configuration>
..
```	
## Test with maven build
To get coverage per tests information, you will need to activate the profile when running the instrumented tests:

```shell
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent -Punit-test test
```

## Jenkins Pipeline configuration
```groovy
try {
        // Any maven phase that that triggers the test phase can be used here.
        sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent -Punit-test test'
    } catch(err) {
//Commend because the xml already generated
//step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    
    //generate html and css/js report
    sh 'mvn surefire-report:report-only -DreportsDirectory=/surefire-reports/unite-test site -DgenerateReports=false'
        throw err
    }
```
