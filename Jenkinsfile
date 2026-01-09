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

        stage('Docker Down') {
            steps {
                bat '''
                    docker-compose down -v --remove-orphans --rmi local || exit 0
                    powershell -Command "Start-Sleep -Seconds 2"
                '''
            }
        }


        stage('Docker Up') {
            steps {
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
