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

        stage('Deploy Artifact to Nexus') {
            steps {
                script {
                    def nexusCredentials = 'nexus-auth' // ID de las credenciales configuradas en Jenkins
                    def nexusUrl = 'https://9146-2800-300-6391-2120-a86e-43aa-3ae-dcf5.ngrok-free.app/'
                    def repository = 'maven-demo-app'
                    def artifact = 'target/demo-app.war'

                    withCredentials([usernamePassword(credentialsId: nexusCredentials, passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USERNAME')]) {
                        sh """
                        curl -u $NEXUS_USERNAME:$NEXUS_PASSWORD --upload-file ${artifact} ${nexusUrl}/repository/${repository}/
                        """
                    }
                }
            }
        }
    }
}
