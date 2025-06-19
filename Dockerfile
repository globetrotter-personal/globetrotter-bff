FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY target/globetrotter-bff-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"] 