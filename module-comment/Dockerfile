FROM openjdk:17-jdk-slim
# EXPOSE 8081

ADD target/*.jar comment.jar
ENTRYPOINT ["java","-jar","comment.jar"]