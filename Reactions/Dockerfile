FROM openjdk:17-jdk-slim
EXPOSE 8045
ADD target/*.jar reactions-service.jar
ENTRYPOINT ["java","-jar","reactions-service.jar"]
