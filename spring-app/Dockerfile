FROM openjdk:11-jdk-slim
EXPOSE 8080
ADD target/*.jar auth-app.jar
ENTRYPOINT ["java","-jar","auth-app.jar"]
