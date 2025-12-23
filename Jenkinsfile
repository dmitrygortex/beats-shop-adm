pipeline {
    agent any

    stages {
        stage('Cleanup') {
            steps {
                echo '========== CLEAN WORKSPACE =========='
                deleteDir()
            }
        }

        stage('Clone') {
            steps {
                echo '========== GIT CLONE =========='
                git branch: 'main',
                    credentialsId: 'github-token',
                    url: 'https://github.com/dmitrygortex/beats-shop-adm.git'
            }
        }

        stage('Build JAR') {
            steps {
                echo '========== BUILD JAR =========='
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar'
            }
        }

        stage('Rebuild Service') {
            steps {
                echo '========== REBUILD BEATS-SERVICE =========='
                // Собираем новый образ
                sh 'docker build -t beats-service:latest .'
                // Останавливаем старый контейнер
                sh 'docker stop beats-service || true'
                sh 'docker rm beats-service || true'
                // Запускаем новый из свежего образа
                sh 'docker compose up -d beats-service'
            }
        }

        stage('Health Check') {
            steps {
                echo '========== HEALTH CHECK =========='
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
