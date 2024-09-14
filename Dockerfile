FROM openjdk:21-jdk
VOLUME /tmp
ARG JAR_FILE=target/shopping-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]