FROM openjdk:8-jdk-alpine
MAINTAINER taquy <taquy.se@gmail.com>
VOLUME /tmp
EXPOSE 7080
ARG JAR_FILE=target/library-management-system-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} lms-practice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/lms-practice.jar"]
