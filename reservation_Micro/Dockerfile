FROM openjdk:17-jdk-slim
EXPOSE 8091
ADD target/*.jar reservation-app.jar
ENTRYPOINT ["java","-jar","reservation-app.jar"]
