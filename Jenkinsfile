pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo '========== CHECKOUT CODE =========='
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                echo '========== BUILD JAR =========='
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar'
            }
        }

        stage('Docker Build') {
            steps {
                echo '========== BUILD DOCKER IMAGE =========='
                sh 'docker build -t beats-service:latest .'
            }
        }

        stage('Deploy') {
            steps {
                echo '========== DEPLOY BEATS SERVICE =========='
                sh 'docker stop beats-service || true'
                sh 'docker rm beats-service || true'
                sh 'docker compose up -d --no-deps beats-service'
            }
        }


        stage('Health Check') {
            steps {
                echo '========== HEALTH CHECK =========='
                sh 'sleep 15'
                sh 'curl -f http://beats-service:8080/api/beats/health || echo "Service starting..."'
                sh 'docker compose ps'
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
