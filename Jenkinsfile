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
                bat 'mvn -Dspring.profiles.active=test test -Dtest=*ServiceTest test'
            }
        }
        stage('Integration Tests') {
            steps {
                bat 'mvn -Dspring.profiles.active=test test -Dtest=*IT test'
            }
        }


        stage('Package') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat '''
                    set DOCKER_BUILDKIT=0
                    docker build -t restaurant-app .
                '''
            }
        }

        stage('Docker Down') {
            steps {
                bat '''
                    docker-compose down --rmi local --volumes --remove-orphans || exit 0
                    powershell -Command "Start-Sleep -Seconds 5"
                '''
            }
        }

        stage('Docker Up') {
            steps {
                bat """
                    ping 127.0.0.1 -n 10 > nul
                    mvn -Dspring.profiles.active=test -Dtest=MenuUITest test
                """
            }
        }
        stage('Selenium Tests') {
            steps {
                bat 'mvn -Dspring.profiles.active=test -Dtest=*UITest test'
            }
        }
    }



    post {
        always {
            junit 'target/surefire-reports/*.xml'
            bat 'docker-compose down --volumes --remove-orphans'
        }
        success {
            echo "🚀 pipeline başarıyla tamamlandı!"
        }
        failure {
            echo "❌ Pipeline fail oldu!"
        }
    }
}
