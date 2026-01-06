FROM maven:3.9.6-eclipse-temurin-17 AS builder

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

ARG ALLURE_VERSION=2.25.0
RUN wget https://github.com/allure-framework/allure2/releases/download/$ALLURE_VERSION/allure-$ALLURE_VERSION.tgz \
    && tar -xzf allure-$ALLURE_VERSION.tgz -C /opt/ \
    && ln -s /opt/allure-$ALLURE_VERSION/bin/allure /usr/bin/allure \
    && rm allure-$ALLURE_VERSION.tgz

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

ENV MAVEN_OPTS="-Dmaven.repo.local=/root/.m2/repository"
ENV JAVA_OPTS="-Xmx1024m -XX:MaxPermSize=256m"

CMD ["sh", "-c", "echo 'Container ready for test execution' && tail -f /dev/null"]