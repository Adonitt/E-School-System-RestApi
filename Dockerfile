# ===============================
# 🏗️ Stage 1: Build the app
# ===============================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Kopjo pom.xml dhe src
COPY pom.xml .
COPY src ./src

# Build jar pa testime (shpejton procesin)
RUN mvn clean package -DskipTests

# ===============================
# 🚀 Stage 2: Run the app
# ===============================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Kopjo jar i prodhuar nga stage 1
COPY --from=build /app/target/*.jar app.jar

# Bind port Spring Boot (Render e përdor PORT environment variable)
EXPOSE 8080

# Nis Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
