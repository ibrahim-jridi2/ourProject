FROM openjdk:17-jdk-slim
EXPOSE 8088
ADD target/*.jar auth.jar
ENTRYPOINT ["java","-jar","auth.jar"]
