FROM maven:3.8-jdk-17
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/*.jar"]