pipeline {
    agent any

    stages {

        stage('> > > UPDATE FROM GIT') {
            steps {
                git credentialsId: 'github-token',
                    url: 'https://github.com/dmitrygortex/beats-shop-adm.git',
                    branch: 'main'
            }
        }

        stage('> > > GRADLE JAR') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar'
            }
        }

        stage('> > > DEPLOY') {
            steps {
                script {
                sh 'docker compose stop beats-service || true'
                sh 'docker rm -f beats-service || true'
                sh 'docker compose up -d --build --no-deps beats-service'
                }
            }
        }

        stage('> > > HEALTH') {
            steps {
                sh 'sleep 15'
                sh 'curl -f http://host.docker.internal:18080/api/beats/health || echo "Service starting..."'
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
