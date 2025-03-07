# Etapa de compilación
FROM maven:3.8-jdk-17
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests