# Use the official OpenJDK image as the base image
FROM openjdk:22-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by Maven (ensure the JAR file name matches your project)
COPY target/user-auth-service-0.0.1-SNAPSHOT.jar user-auth-service.jar

# Expose the port on which the Spring Boot application runs (if applicable)
EXPOSE 8081

# Command to run the Spring Boot application inside the container
CMD ["java", "-jar", "user-auth-service.jar"]
