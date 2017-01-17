#Some snippets about Pipeline

##Render Cucumber Report

```groovy

def renderCucumberReport(jsonReportDirectory){ step([$class: 'CucumberReportPublisher',    fileExcludePattern: '',    fileIncludePattern: '**/*.json',    ignoreFailedTests: false,    jenkinsBasePath: '',    jsonReportDirectory: jsonReportDirectory,    missingFails: false,    parallelTesting: false,    pendingFails: false,    skippedFails: false,    undefinedFails: false])}

```
##Scan with SonarQube

```groovy
withSonarQubeEnv(serverName) {      // requires SonarQube Scanner for Maven 3.2+      command = "mvn -f ${pomRep} org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar"      sh command    }
```

##Publish HTML

```groovy

publishHTML (target: [      allowMissing: false,      alwaysLinkToLastBuild: false,      keepAll: true,      reportDir: reportDir,      reportFiles: reportFiles,      reportName: reportName    ])
```

##Retrieve artifact version from POM

```groovy
def matcher = readFile(pomRep) =~ '<version>(.+)</version>'   artifactVersion = matcher ? matcher[0][1] : null    return artifactVersion
```

##Transfer Map to List

```groovy
@NonCPSdef mapToList(depmap) {    def dlist = []    for (def entry2 in depmap) {        dlist.add(new java.util.AbstractMap.SimpleImmutableEntry(entry2.key, entry2.value))    }    dlist}
```
##Iterate (loop) the map
```groovy
def printMap(map){ for(def e in mapToList(map)){  echo "key = ${e.key}, value = ${e.value}" }}
```
