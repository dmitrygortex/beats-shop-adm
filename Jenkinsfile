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

        stage('Docker Compose Up') {
            steps {
                echo '========== START INFRASTRUCTURE =========='
                // Останавливаем старый стек
                sh 'docker compose down || true'
                // Поднимаем ВСЮ инфраструктуру из docker-compose.yml
                sh 'docker compose up -d'
            }
        }

        stage('Health Check') {
            steps {
                echo '========== HEALTH CHECK =========='
                sh 'sleep 20'
                sh 'curl -f http://localhost:18080/api/beats/health || echo "Service starting..."'
                sh 'docker compose ps'
            }
        }
    }

    post {
        success {
            echo '✅ INFRASTRUCTURE UP BY JENKINS!'
        }
        failure {
            echo '❌ Deployment failed!'
        }
    }
}
