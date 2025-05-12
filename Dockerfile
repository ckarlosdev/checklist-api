FROM openjdk:17-jdk-slim

COPY target/mi-primer-api-rest-0.0.1-SNAPSHOT.jar /api-v1.jar

ENTRYPOINT  ["java", "-jar", "/api-v1.jar"]