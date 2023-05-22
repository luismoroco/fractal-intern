FROM adoptopenjdk:14-jdk-hotspot

WORKDIR /app

COPY build/libs/challenge-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]