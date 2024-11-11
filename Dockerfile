FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/holdme-api-0.0.1.jar

COPY ${JAR_FILE} holdme-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "holdme-api.jar"]