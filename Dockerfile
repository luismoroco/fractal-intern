FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build.gradle .

RUN java -jar /opt/gradle/gradle-7.0.2/lib/gradle-launcher.jar wrapper --gradle-version 7.0.2

COPY . .

RUN ./gradlew build

CMD ["./gradlew", "bootRun"]