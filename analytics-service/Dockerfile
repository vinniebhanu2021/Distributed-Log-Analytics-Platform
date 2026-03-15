FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY common ./common
COPY ingestion-service ./ingestion-service
COPY processor-service ./processor-service
COPY analytics-service ./analytics-service
RUN mvn clean package -DskipTests -pl analytics-service -am

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/analytics-service/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
