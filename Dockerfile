FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY dutyBot/target/*.jar app.jar
COPY dutyBot/target/*.json grath.jar
COPY dutyBot/target/*.txt log.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 1488