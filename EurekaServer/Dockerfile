FROM openjdk:17.0-jdk

WORKDIR /app

COPY EurekaServer-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8761

CMD ["java", "-jar", "app.jar"]