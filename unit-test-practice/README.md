# Java Unit Report, jUnit + Jacoco Maven Snippets

A short demonstration how to set-up junit and report in a mavenized Java environment.

How to get code coverage by Jacoco

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
	<testFailureIgnore>true</testFailureIgnore>
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
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Punit-test
```