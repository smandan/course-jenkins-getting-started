pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
//  tools {
//         // Install the Maven version configured as "M3" and add it to the path.
//         maven "M3"
//  }

    stages {
        stage('Checkout') {
            steps {
            // Get some code from a GitHub repository
            git branch: 'main', url: 'https://github.com/smandan/jgsu-spring-petclinic.git'

            }
        }

        stage('Build') {
            steps {
//              Run Maven on a Unix agent.
//              sh "mvn -Dmaven.test.failure.ignore=true clean package"
//              sh "mvn spring-javaformat:apply clean package"
                sh "mvn spring-javaformat:apply -Dmaven.test.failure.ignore=true clean package"
//              sh "false" // true
//              sh "true"  // false

//              To run Maven on a Windows agent, use
//              bat "mvn spring-javaformat:apply  -Dmaven.test.failure.ignore=true clean package"
            }
            post {
//              If Maven was able to run the tests, even if some of the test
//              failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
                changed {
                    emailext attachLog: true,
                    body: 'Please go to ${BUILD_URL} and verify the build',
                    compressLog: true,
                    recipientProviders: [upstreamDevelopers(), requestor()],
                    subject: "Job ${JOB_NAME} (build ${BUILD_NUMBER}) ${currentBuild.result}",
                    to: 'mandas1@nationwide.com s.gadepalli@nationwide.com'
                }
            }
        }

    }
}