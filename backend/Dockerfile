# ===== Stage 1: Maven build =====
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy Maven wrapper and POM first (layer cache optimization)
COPY pom.xml .
RUN apk add --no-cache maven && mvn dependency:go-offline -q

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -q

# ===== Stage 2: Slim JRE runtime =====
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Non-root user for security
RUN addgroup -S campusconnect && adduser -S campusconnect -G campusconnect
USER campusconnect

# Copy the fat JAR from build stage
COPY --from=builder /app/target/campusconnect-backend-1.0.0.jar app.jar

# Render assigns PORT via env var
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
