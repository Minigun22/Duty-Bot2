FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY dutyBot/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 1488