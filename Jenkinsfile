pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo '1. Checkout code from Git'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '2. Build Spring Boot JAR'
                sh './gradlew clean bootJar'
            }
        }

        stage('Docker Build') {
            steps {
                echo '3. Build Docker image'
                sh 'docker build -t beats-service:latest .'
            }
        }

        stage('Deploy') {
            steps {
                echo '4. Deploy with docker-compose'
                sh 'docker compose down'
                sh 'docker compose up -d'
            }
        }

        stage('Health Check') {
            steps {
                echo '5. Check services'
                sh 'sleep 10'
                sh 'curl -f http://localhost:18080/api/beats/health || true'
                sh 'curl -f http://localhost:9099/targets || true'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished!'
            sh 'docker compose ps'
        }
    }
}
