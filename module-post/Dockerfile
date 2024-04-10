FROM openjdk:11-jdk-slim
# EXPOSE 8081

ADD target/*.jar post.jar
ENTRYPOINT ["java","-jar","post.jar"]