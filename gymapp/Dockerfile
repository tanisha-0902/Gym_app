# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# We use the subfolder 'gymapp' because of your nested structure
COPY . .
RUN mvn -f gymapp/pom.xml clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Grabs the built jar from the build stage
COPY --from=build /app/gymapp/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]