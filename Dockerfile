FROM maven:3.8.5-openjdk-17 AS build
COPY --from=build /target/*.jar app.jar
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/load-board-0.0.1-SNAPSHOT.jar load-board.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","load-board.jar"]

