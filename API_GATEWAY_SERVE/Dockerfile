FROM openjdk:11-jdk-slim
# EXPOSE 9999
ADD target/*.jar gateway.jar
ENTRYPOINT ["java","-jar","gateway.jar"]
