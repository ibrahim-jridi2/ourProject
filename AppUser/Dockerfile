FROM openjdk:17-jdk
EXPOSE 8070
ADD target/AppUser-0.0.1-SNAPSHOT.jar app-user.jar
ENTRYPOINT ["java", "-jar", "/app-user.jar"]
