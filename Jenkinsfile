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
                bat 'docker-compose up -d'
                echo "Sistemin hazır olması bekleniyor..."
                bat 'ping 127.0.0.1 -n 30 > nul'
            }
        }

        pipeline {
            agent any
            stages {
                stage('Build & Docker Run') {
                    steps {
                        bat 'docker-compose down'
                        bat 'docker-compose up -d --build'
                    }
                }

                stage('Selenium Tests') {
                    steps {
                        echo 'Sistemin (Docker) hazır olması bekleniyor...'
                        // Windows Jenkins için en stabil bekleme yöntemi:
                        bat 'ping 127.0.0.1 -n 45 > nul'

                        echo 'Selenium testleri baslatiliyor...'
                        // Paket yolunu kendi projenizle birebir eşleyin
                        bat 'mvn -Dtest=com.ydg.restaurant.selenium.MenuUITest test'
                    }
                }
            }
            post {
                always {
                    // Testler calıstıktan sonra raporları tara
                    junit '**/target/surefire-reports/*.xml'
                }
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
