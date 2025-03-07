FROM tomcat:9.0-jdk17-slim
COPY build/libs/routes-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/routes-0.0.1-SNAPSHOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]