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

        stage('Prepare DB for Integration Tests') {
            steps {
                bat '''
                docker compose down || exit 0
                docker compose up -d db
                '''
            }
        }

        stage('Unit Tests') {
            steps {
                bat "mvn -Dtest=*ServiceTest test"
            }
        }

        stage('Start Test DB') {
            steps {
                bat 'docker-compose -f docker-compose.test.yml up -d'
                bat 'timeout /t 10'
            }
        }

        stage('Integration Tests') {
            steps {
                bat 'mvn test -Dspring.profiles.active=integration -Dtest=**/*IntegrationTest'
            }
        }

        stage('Package') {
            steps {
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Docker Build & Run') {
            steps {
                bat '''
                    docker build -t restaurant-app .
                    docker compose up -d app
                    '''
            }
        }
    }

    post {
        always {
            bat "docker compose down || exit 0"
        }
        success {
            echo "🎉 Build + Test + Docker başarıyla tamamlandı!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
    }
}
