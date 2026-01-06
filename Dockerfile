FROM maven:3.9.6-eclipse-temurin-17 AS builder

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    && rm -rf /var/lib/apt/lists/*

ARG ALLURE_VERSION=2.25.0
RUN wget https://github.com/allure-framework/allure2/releases/download/$ALLURE_VERSION/allure-$ALLURE_VERSION.tgz \
    && tar -xzf allure-$ALLURE_VERSION.tgz -C /opt/ \
    && ln -s /opt/allure-$ALLURE_VERSION/bin/allure /usr/bin/allure \
    && rm allure-$ALLURE_VERSION.tgz

RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

WORKDIR /app

COPY . .

COPY pom.xml .
RUN mvn dependency:go-offline -B

VOLUME ["/root/.m2"]

ENV MAVEN_OPTS="-Dmaven.repo.local=/root/.m2/repository"
ENV JAVA_OPTS="-Xmx1024m -XX:MaxPermSize=256m"

CMD ["mvn", "clean", "test", "allure:report"]