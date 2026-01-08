pipeline {
    agent any

    tools {
        maven 'M3'
        jdk 'jdk-21'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yeliznurkilic/YDG-Restoran-Sistemi.git'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test -Dtest=*Test'
            }
        }

        stage('Integration Tests') {
            steps {
                sh 'mvn test -Dtest=*IntegrationTest'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Compose Up') {
            steps {
                sh '''
                docker-compose down || true
                docker-compose up -d --build
                echo "🚀 App running on http://localhost:9090"
                '''
            }
        }
    }

    post {
        always {
            sh 'docker-compose down || true'
        }
        success {
            echo '🎉 Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
