#FROM eclipse-temurin:17-jdk
#
#COPY target/mi-primer-api-rest-0.0.1-SNAPSHOT.jar /api-v1.jar
#
#ENTRYPOINT  ["java", "-jar", "/api-v1.jar"]

FROM maven:3.9-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Crear la imagen de ejecución (Run stage)
FROM eclipse-temurin:17-jdk-jammy
# Buscamos el jar generado en el paso anterior
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]