#Maven command

##Find availabe goal of plugin
```sh
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-surefire-report-plugin:2.19.1
```
##Discover plugin goals and parameters
```sh
mvn help:describe -Dmojo=describe -DgroupId=org.apache.maven.plugins -DartifactId=maven-surefire-report-plugin

```

##Surefire report plugin
```sh
mvn surefire-report:report-only -DreportsDirectories = 
```

##Site generate css and image only
```sh
mvn site: -DgenerateReports=false
```
