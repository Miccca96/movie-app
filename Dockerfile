FROM openjdk:11 AS build
WORKDIR src/
COPY build/libs/movie-app-0.0.1-SNAPSHOT.jar app.jar
COPY build/resources/main/movies.json movies.json
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]