FROM maven:3.9.1-eclipse-temurin-17-alpine AS MAVEN_BUILD

RUN mkdir -p /app/source

COPY . /app/source

WORKDIR /app/source

RUN mvn  clean package  -DskipTests


FROM openjdk:11-jre

COPY --from=MAVEN_BUILD /app/source/target/*.jar /app/app.jar

# Refer to Maven build -> finalName
# cd /opt/app
WORKDIR /app

#ARG JAR_FILE=/app/*.jar


# cp target/spring-boot-web.jar /opt/app/app.jar
#COPY ${JAR_FILE} app.jar



EXPOSE 8084

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]