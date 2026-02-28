# Use an official Maven image to build the application
FROM maven:3.9.5-eclipse-temurin-21-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

# Use an official OpenJDK image to run the application
FROM eclipse-temurin:21-jre-slim
WORKDIR /app
COPY --from=build /app/target/flat_manager-0.0.1-SNAPSHOT.war .
ENV PORT 8080
EXPOSE 8080
CMD ["java", "-jar", "flat_manager-0.0.1-SNAPSHOT.war"]
