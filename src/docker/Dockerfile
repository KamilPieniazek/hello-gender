FROM openjdk:12-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} /app/hello-gender-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/hello-gender-app.jar"]
