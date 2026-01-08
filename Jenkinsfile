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

        stage('Unit + Integration Tests') {
            steps {
                bat 'mvn -Dspring.profiles.active=test test'
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

        stage('Docker Up') {
            steps {
                bat 'docker-compose down || exit 0'
                bat 'docker-compose up -d --build'
            }
        }
    }

    post {
        success {
            echo "🚀 CI pipeline başarıyla tamamlandı!"
        }
        failure {
            echo "❌ Pipeline fail oldu!"
        }
    }
}
