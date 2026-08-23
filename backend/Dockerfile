# ===== Stage 1: Maven build =====
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests --no-transfer-progress

# ===== Stage 2: Slim JRE runtime =====
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

RUN addgroup -S campusconnect && adduser -S campusconnect -G campusconnect
USER campusconnect

COPY --from=builder /app/target/app.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
