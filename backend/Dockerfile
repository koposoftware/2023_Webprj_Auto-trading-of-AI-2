# Build stage
FROM openjdk:11-jdk-slim AS build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew \
    && sed -i 's/\r$//' ./gradlew \
    && ./gradlew build

# Package stage
FROM openjdk:11-jdk-slim
COPY --from=build /app/build/libs/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
