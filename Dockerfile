FROM openjdk:15-jdk-alpine
ARG JAR_FILE=sensor-stats-ingester-app/target/*.jar
COPY ${JAR_FILE} sensor-stats-ingester-app.jar
ENTRYPOINT ["java","-jar","/sensor-stats-ingester-app.jar"]