FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} java-app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","java-app.jar"]