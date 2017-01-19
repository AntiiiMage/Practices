#Some snippets about Jenkins Pipeline
All compatible plug-in list can be find [here](https://github.com/jenkinsci/pipeline-plugin/blob/master/COMPATIBILITY.md)

##Check out from SVN
```groovy
checkout([$class: 'SubversionSCM', 
          additionalCredentials: [], 
          excludedCommitMessages: '', 
          excludedRegions: '', 
          excludedRevprop: '', 
          excludedUsers: '', 
          filterChangelog: false, 
          ignoreDirPropChanges: false, 
          includedRegions: '', 
          locations: [[credentialsId: '34761a89-1402-47d7-96e2-aec22ffdc50b', 
                       depthOption: 'infinity', 
                       ignoreExternalsOption: true, 
                       local: 'cable_branch', 
                       remote: 'https://trac.nci.org.au/svn/cable/branches/$SVN_BRANCH']], 
          workspaceUpdater: [$class: 'UpdateUpdater']])
```

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
def version() {
  def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
  matcher ? matcher[0][1] : null
}
```
* =~ is Groovy syntax to match text against a regular expression
* [0] looks up the first match
* [1] the first (…) group within that match

##Transfer Map to List (for loop task)

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

##Declarative way to buil parallel jobs
```groovy
node {
   git "git@github.com..."
   parallel (
     phase1: { sh "echo p1; sleep 20s; echo phase1" },
     phase2: { sh "echo p2; sleep 40s; echo phase2" }
   )
  sh "run this after both phases complete"   
}
```

##Build Parallel map from List
```groovy
// Our initial list of strings we want to echo in parallel
def stringsToEcho = ["a", "b", "c", "d"]

// The map we'll store the parallel steps in before executing them.
def stepsForParallel = [:]

// The standard 'for (String s: stringsToEcho)' syntax also doesn't work, so we
// need to use old school 'for (int i = 0...)' style for loops.
for (int i = 0; i < stringsToEcho.size(); i++) {
    // Get the actual string here.
    def s = stringsToEcho.get(i)

    // Transform that into a step and add the step to the map as the value, with
    // a name for the parallel step as the key. Here, we'll just use something
    // like "echoing (string)"
    def stepName = "echoing ${s}"
    
    stepsForParallel[stepName] = transformIntoStep(s)
}

// Actually run the steps in parallel - parallel takes a map as an argument,
// hence the above.
parallel stepsForParallel

// Take the string and echo it.
def transformIntoStep(inputString) {
    // We need to wrap what we return in a Groovy closure, or else it's invoked
    // when this method is called, not when we pass it to parallel.
    // To do this, you need to wrap the code below in { }, and either return
    // that explicitly, or use { -> } syntax.
    return {
        node {
            echo inputString
        }
    }
}
parallel branches
```
##Load from file
###External Groovy: look.groovy
```Groovy
// Methods in this file will end up as object methods on the object that load returns.
def lookAtThis(String whoAreYou) {
    echo "Look at this, ${whoAreYou}! You loaded this from another file!"
}

return this;
```
###Pipeline Script: jenkinsjob
```groovy
node {
    // Load the file 'look.groovy' from the current directory, into a variable called "externalMethod".
    def externalMethod = load("look.groovy")

    // Call the method we defined in externalMethod.
    externalMethod.lookAtThis("Steve")
}
```

##Recording Test Results and Artifacts
Instead of failing the build if there are test failures, you want Jenkins to record them — and then proceed. If you want it saved, you must capture the JAR that you built.

```groovy
node {
  git url: 'https://github.com/jglick/simple-maven-project-with-tests.git'
  def mvnHome = tool 'M3'
  sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore verify"
  step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
  step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
}
```

If tests fail, the Pipeline is marked unstable (yellow ball), and you can browse the Test Result Trend to see the history.
You should see Last Successful Artifacts on the Pipeline index page.

##SSH Agent
```groovy
node {
  sshagent (credentials: ['deploy-dev']) {
    sh 'ssh -o StrictHostKeyChecking=no -l cloudbees 192.168.1.106 uname -a'
  }
}
```