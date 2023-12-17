FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY dutyBot/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 1488