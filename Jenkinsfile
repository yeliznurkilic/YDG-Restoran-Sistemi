pipeline {
    agent any

    stages {

        // -------------------------------------------------
        // 1. GitHub'dan projeyi çek
        // -------------------------------------------------
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yeliznurkilic/YDG-Restoran-Sistemi.git'
            }
        }


        // -------------------------------------------------
        // 2. Maven testlerini çalıştır (Birim + Entegrasyon)
        // -------------------------------------------------
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        // -------------------------------------------------
        // 3. Uygulamayı paketle (jar üret)
        // -------------------------------------------------
        stage('Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        // -------------------------------------------------
        // 4. Docker image oluştur
        // -------------------------------------------------
        stage('Docker Build') {
            steps {
                sh 'docker build -t restaurant-app .'
            }
        }

        // -------------------------------------------------
        // 5. Docker-compose ile çalıştır (deploy)
        // -------------------------------------------------
        stage('Deploy') {
            steps {
                sh 'docker compose up -d'
            }
        }
    }
}
