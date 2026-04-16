# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy everything from the current directory (which Render sets as 'gymapp')
COPY . .

# Run maven on the pom.xml in the current directory
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built jar. Note: it will be in the 'target' folder of the current directory
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]