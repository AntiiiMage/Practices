#Some snippets about Pipeline

##Render Cucumber Report

```groovy

def renderCucumberReport(jsonReportDirectory){ 
	step([$class: 'CucumberReportPublisher',    
		fileExcludePattern: '',    
		fileIncludePattern: '**/*.json',    
		ignoreFailedTests: false,    
		jenkinsBasePath: '',    
		jsonReportDirectory: jsonReportDirectory,    
		missingFails: false,    
		parallelTesting: false,    
		pendingFails: false,    
		skippedFails: false,    
		undefinedFails: false])
}

```
##Scan with SonarQube

```groovy
withSonarQubeEnv(serverName) {      
	// requires SonarQube Scanner for Maven 3.2+      
	command = "mvn -f ${pomRep} org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar"      
	sh command    
}
```

##Publish HTML

```groovy

publishHTML (target:[
	allowMissing: false,
	alwaysLinkToLastBuild: false,
	keepAll: true,
	reportDir: reportDir,
	reportFiles: reportFiles,
	reportName: reportName]
	)
```

##Retrieve artifact version from POM

```groovy
def matcher = readFile(pomRep) =~ '<version>(.+)</version>'
artifactVersion = matcher ? matcher[0][1] : null
return artifactVersion
```

##Transfer Map to List

```groovy
@NonCPS
def mapToList(depmap) {
	def dlist = []
	for (def entry2 in depmap){
		dlist.add(new java.util.AbstractMap.SimpleImmutableEntry(entry2.key, entry2.value))
		}
	dlist
}
```
##Artifactory - Working With the Pipeline Jenkins Plugin
```
https://wiki.jenkins-ci.org/display/JENKINS/Artifactory+-+Working+With+the+Pipeline+Jenkins+Plugin
```

##Run maven
```groovy
/* Run maven from tool "mvn" */
void mvn(def args) {
    /* Get the maven tool. */
    def mvnHome = tool name: 'maven339'
    //def mvnHome = tool 'maven339'

    def settings = ' -s settings.xml'
    /* Call maven tool with java envVars. */
    if (isUnix()) {
        sh "${mvnHome}/bin/mvn ${args}"+settings
    } else {
        bat "${mvnHome}\\bin\\mvn ${args}"+settings
    }
}
```

##Build Parallel map
```groovy
node('remote') {
  git 'https://github.com/jenkinsci/parallel-test-executor-plugin-sample.git'
  stash name: 'sources', includes: 'pom.xml,src/'
}
def splits = splitTests count(2)
def branches = [:]
for (int i = 0; i < splits.size(); i++) {
  def index = i // fresh variable per iteration; i will be mutated
  branches["split${i}"] = {
    node('remote') {
      deleteDir()
      unstash 'sources'
      def exclusions = splits.get(index);
      writeFile file: 'exclusions.txt', text: exclusions.join("\n")
      sh "${tool 'M3'}/bin/mvn -B -Dmaven.test.failure.ignore test"
      junit 'target/surefire-reports/*.xml'
    }
  }
}
parallel branches
```
##Parallel 
https://jenkins.io/blog/2016/06/16/parallel-test-executor-plugin/
https://www.cloudbees.com/blog/parallelism-and-distributed-builds-jenkins
