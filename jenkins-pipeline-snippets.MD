#Some snippets about Pipeline

##Render Cucumber Report

```groovy

def renderCucumberReport(jsonReportDirectory){ step([$class: 'CucumberReportPublisher',    fileExcludePattern: '',    fileIncludePattern: '**/*.json',    ignoreFailedTests: false,    jenkinsBasePath: '',    jsonReportDirectory: jsonReportDirectory,    missingFails: false,    parallelTesting: false,    pendingFails: false,    skippedFails: false,    undefinedFails: false])}

```
