# Use a base image with Java 11 installed
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file into the container
COPY target/entrevista.0.0.1.jar app.jar

# Expose the port on which your Spring Boot app listens
EXPOSE 8080

# Set the command to run your Spring Boot app when the container starts
CMD ["java", "-jar", "app.jar"]
