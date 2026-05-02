# Build stage
FROM maven:3.9.7-eclipse-temurin-17 AS build
LABEL authors="angelo"

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/real-time-chat-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Health check (opcional)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD java -cp app.jar org.springframework.boot.loader.JarLauncher || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
