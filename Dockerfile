# Java 21 içeren resmi Temurin JDK image kullanıyoruz
FROM eclipse-temurin:21-jdk

# Container içinde çalışma dizini /app olsun
WORKDIR /app

# =====================================================
# Maven'in ürettiği jar dosyasını içeri kopyalıyoruz
# '*.jar' -> versiyon değişse bile çalışır
# app.jar -> tek isimle sabit çalıştırırız
# =====================================================
COPY target/*.jar app.jar

# Uygulama 8080 portunda çalışıyor -> dışa açıyoruz
EXPOSE 8080

# Spring Boot jar'ı çalıştır
ENTRYPOINT ["java","-jar","app.jar"]
