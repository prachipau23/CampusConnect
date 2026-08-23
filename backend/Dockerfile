# ===== Stage 1: Maven build =====
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy pom.xml and cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build jar
COPY src ./src
RUN mvn clean package -DskipTests -B

# ===== Stage 2: Slim JRE runtime =====
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Non-root user for security
RUN addgroup -S campusconnect && adduser -S campusconnect -G campusconnect
USER campusconnect

# Copy the packaged JAR from build stage
COPY --from=builder /app/target/campusconnect-backend-1.0.0.jar app.jar

# Render assigns PORT via environment variable
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
