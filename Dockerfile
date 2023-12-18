FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY dutyBot/target/*.jar app.jar
COPY dutyBot/*.json repo.json
COPY dutyBot/*.txt log.txt
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 1488