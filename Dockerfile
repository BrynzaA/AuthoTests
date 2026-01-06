FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY test-resources ./test-resources

RUN mkdir -p /app/target/allure-results \
    && mkdir -p /app/target/screenshots \
    && mkdir -p /app/target/cookies \
    && mkdir -p /app/target/downloads

RUN mvn dependency:go-offline -B