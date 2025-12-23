pipeline {
    agent any

    stages {
        stage('> > > DELETE DIR') {
            steps {
                deleteDir()
            }
        }

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
                    sh 'docker compose -p beats-project stop beats-service || true'
                    sh 'docker rm -f beats-service || true'
                    sh 'docker compose -p beats-project up -d --build --no-deps beats-service'
                }
            }
        }

        stage('> > > HEALTH') {
            steps {
                sh 'sleep 15'
                sh 'curl -f http://localhost:8080/api/beats/health || echo "Service starting..."'
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
