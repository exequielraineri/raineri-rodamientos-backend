FROM openjdk:17
LABEL authors="Exequiel"
ARG JAR_FILE=target/api_raineri_rodamientos.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 9090