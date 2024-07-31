pipeline {
    agent any

    stages {
        stage('SCM') {
            steps {
                checkout scm
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    def mvn = tool 'Maven 3.8.1' // Usa el nombre exacto configurado
                    withSonarQubeEnv() {
                        sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.host.url=http://${SONARQUBE_URL}:9000 -Dsonar.projectKey=demo-app -Dsonar.projectName=demo-app"
                    }
                }
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

        // stage('Deploy Artifact to Nexus') {
        //     steps {
        //             script {
        //                 def nexusUrl = 'https://9146-2800-300-6391-2120-a86e-43aa-3ae-dcf5.ngrok-free.app/repository/maven-demo-app/'
        //                 def artifact = 'target/demo-app.war'

        //                 // Uso de las variables de entorno
        //                 sh """
        //                 curl -u $NEXUS_USERNAME:$NEXUS_PASSWORD --upload-file ${artifact} ${nexusUrl}
        //                 """
        //         }
        //     }
        // }
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
