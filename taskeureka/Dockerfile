# Use the official OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/taskeureka-0.0.1.jar /app/taskeureka-0.0.1.jar

# Expose the port on which the application will run
EXPOSE 8001

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "taskeureka-0.0.1.jar"]
