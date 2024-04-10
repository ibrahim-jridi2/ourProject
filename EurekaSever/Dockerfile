FROM openjdk:17-jdk-slim
EXPOSE 8761
ADD target/*.jar EurekaServer.jar
ENTRYPOINT ["java","-jar","EurekaServer.jar"]
