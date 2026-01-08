pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t restaurant-app .'
            }
        }

        stage('Run with Docker Compose') {
            steps {
                sh 'docker compose up -d --build'
            }
        }
    }

    post {
        always {
            echo 'Pipeline Finished.'
        }
    }
}
