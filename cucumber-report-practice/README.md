# Cucumber, Java, Report, jUnit + Maven Snippets

A short demonstration how to set-up cucumber and report in a mavenized Java environment.

##Add dependencies:
	```xml
		<dependency>
			<groupId>com.googlecode.totallylazy</groupId>
			<artifactId>totallylazy</artifactId>
			<version>1.86</version>
		</dependency>
		<dependency>
			<groupId>net.masterthought</groupId>
			<artifactId>cucumber-reporting</artifactId>
			<version>3.4.0</version>
		</dependency>
	```xml

##Configure Plugin
###Surefire plugin:
	```xml
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-surefire-plugin</artifactId>
			<version>2.18.1</version>
			<executions>
				<execution>
					<id>defult-test</id>
					<phase>test</phase>
					<goals>
						<goal>test</goal>
					</goals>
					<configuration>
						<testFailureIgnore>true</testFailureIgnore>
						<includes>
							<include>**feature**</include>
						</includes>
					</configuration>
				</execution>
			</executions>
		</plugin>
	```		
###Cucumber Reporting plugin
	```xml
	<plugin>
		<groupId>net.masterthought</groupId>
		<artifactId>maven-cucumber-reporting</artifactId>
		<version>3.4.0</version>
		<executions>
			<execution>
				<id>execution</id>
				<phase>test</phase>
				<goals>
					<goal>generate</goal>
				</goals>
				<configuration>
					<projectName>cucumber-jvm-example</projectName>
					<outputDirectory>${project.build.directory}/site/cucumber-reports</outputDirectory>
					<cucumberOutput>${project.build.directory}/cucumber.json</cucumberOutput>
					<enableFlashCharts>false</enableFlashCharts>
					<skippedFails>true</skippedFails>
				</configuration>
			</execution>
		</executions>
	</plugin>
	```

## Test class configuration
```java
@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "json:target/cucumber.json" })
public class BookSearchTest {
}
```