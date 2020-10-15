// Powered by Infostretch 

timestamps {

node () {

	stage ('spring-petclinic - Checkout') {
 	 checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/smandan/jgsu-spring-petclinic.git']]]) 
	}
	stage ('spring-petclinic - Build') {
 			// Shell build step
sh """ 
mvn spring-javaformat:apply clean package
# ./mvnw spring-javaformat:apply clean package 
 """
		archiveArtifacts allowEmptyArchive: false, artifacts: 'target/*.jar', caseSensitive: true, defaultExcludes: true, fingerprint: false, onlyIfSuccessful: false
		// JUnit Results
		junit 'target/surefire-reports/*.xml' 
	}
}
}