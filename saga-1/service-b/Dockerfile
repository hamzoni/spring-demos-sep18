FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=target/service-b-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} service-b.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service-b.jar"]

