FROM maven:3.8.2-openjdk-11 as build
WORKDIR /app
COPY . .
RUN mvn package


FROM openjdk:11
WORKDIR /app
COPY --from=build /app/target/ ./
ENTRYPOINT ["java", "-jar", "shortener-url-0.0.1-SNAPSHOT.jar"]