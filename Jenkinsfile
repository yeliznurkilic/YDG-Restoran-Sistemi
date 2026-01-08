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
                bat 'git --version'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test -Dtest=*Test'
            }
        }

        stage('Integration Tests') {
            steps {
                bat 'mvn test -Dtest=*IntegrationTest'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t restaurant-app .'
            }
        }

        stage('Docker Compose Up') {
            steps {
                bat 'docker-compose up -d'
            }
        }
    }

    post {
        always {
            bat 'docker-compose down || exit 0'
        }
        success {
            echo "🎉 Başarılı!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
    }
}
