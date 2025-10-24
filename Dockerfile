# 🏗️ Stage 1: Build app brenda Docker-it
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Kopjo pom.xml dhe src
COPY pom.xml .
COPY src ./src

# Build project dhe krijo jar
RUN mvn clean package -DskipTests

# 🚀 Stage 2: Run app
FROM eclipse-temurin:21-jre

WORKDIR /app

# Kopjo jar-in e build-uar nga stage 1
COPY --from=build /app/target/*.jar ./eschool.jar

# Ekspono portin që do përdorë Spring Boot
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "eschool.jar"]
