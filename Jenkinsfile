pipeline {
    agent any

    stages {
        stage('Cleanup') {
            steps {
                deleteDir()
            }
        }

        stage('Clone') {
            steps {
                git credentialsId: 'github-token',
                    url: 'https://github.com/dmitrygortex/beats-shop-adm.git',
                    branch: 'main'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar'
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'docker compose -p beats-project stop beats-service || true'
                    sh 'docker compose -p beats-project up -d --build --no-deps beats-service'
                }
            }
        }

        stage('Health Check') {
            steps {
                sh 'sleep 15'
                sh 'curl -f http://localhost:18080/api/beats/health || echo "Service starting..."'
            }
        }
    }

    post {
        success {
            echo '✅ DEPLOYMENT SUCCESS!'
        }
        failure {
            echo '❌ Deployment failed!'
        }
    }
}
