FROM tomcat:9.0-jdk17-slim # Usar una imagen Tomcat con JDK 17
COPY build/libs/tu-aplicacion.war /usr/local/tomcat/webapps/tu-aplicacion.war # Copiar el .war
EXPOSE 8080 # Exponer el puerto de Tomcat
CMD ["catalina.sh", "run"] # Iniciar Tomcat