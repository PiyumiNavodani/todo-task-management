# Use OpenJDK 21
FROM eclipse-temurin:21-jdk-jammy

# Working directory
WORKDIR /app

# Copy and rename JAR
COPY target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
