FROM openjdk:17-jdk-slim-buster
LABEL authors="senty"


COPY /target/Hotels_pet-0.0.1-SNAPSHOT.jar target/

EXPOSE 8092

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=postgres", "./target/Hotels_pet-0.0.1-SNAPSHOT.jar"]