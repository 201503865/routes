FROM openjdk:17-jdk-slim
ARG JAR_FILE=routes/build/libs/*-?.?.?.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]