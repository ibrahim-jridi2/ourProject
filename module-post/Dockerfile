FROM openjdk:17-jdk-slim
# EXPOSE 8081

ADD target/*.jar post.jar
ENTRYPOINT ["java","-jar","post.jar"]