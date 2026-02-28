# Use an official Maven image to build the application
FROM maven:3.8.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Use an official OpenJDK image to run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/flat_manager-0.0.1-SNAPSHOT.war .
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "flat_manager-0.0.1-SNAPSHOT.war"]
