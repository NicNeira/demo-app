pipeline {
    agent any

    environment {
        //configuración de la herramienta
        scannerHome = tool 'Sonar'
    }

    stages {
        stage('SCM') {
            steps {
                checkout scm
            }
        }

        
        stage('Build') {
            steps {
                echo 'Building...'
                // Aquí puedes agregar tus comandos de compilación, por ejemplo:
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
                // Aquí puedes agregar tus comandos de prueba, por ejemplo:
                sh 'mvn test'
            }
        }

        stage('Sonar Analysis') {
            steps {
                echo 'Sonar Analysis'
                withSonarQubeEnv('Sonar') {
                    sh """
                        ${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=demo-app \
                        -Dsonar.sources=web/ \
                        -Dsonar.junit.reportsPath=target/surefire-reports/ \
                        -Dsonar.jacoco.reportsPath=target/jacoco.exec \
                        -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Upload Artifact') {
            steps {
            nexusArtifactUploader(
                nexusVersion: 'nexus3',
                protocol: 'http',
                nexusUrl: 'localhost:8081',
                groupId: 'QA',
                version: “${env.BUILD_ID}-${env.BUILD_TIMESTAMP}”,
                repository: 'time-tracker-repo',
                credentialsId: 'NexusLogin',
                artifacts: [
                    [artifactId: 'webApp',
                    classifier: '',
                    file: 'web/target/time-tracker-web-0.5.0-SNAPSHOT.war',
                    type: 'war'],
                    [artifactId: 'coreApp',
                    classifier: '',
                    file: 'core/target/time-tracker-core-0.5.0-SNAPSHOT.jar',
                    type: 'jar']
                ]
            )
            }
        }
    post {
        always {
            script {
                def COLOR_MAP = [
                    'SUCCESS': 'good',
                    'FAILURE': 'danger'
                ]
                def resultColor = COLOR_MAP[currentBuild.currentResult.toString()]
                slackSend(channel: '#tarea-clase-10', 
                            color: resultColor, 
                            message: "**${currentBuild.currentResult}**: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\nMore Info at: ${env.BUILD_URL}"
                        )
                }
            }
        }
    } 
}  
