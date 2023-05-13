FROM openjdk:11-jre
VOLUME /tmp
ADD target/springBootDocker-1.0.0.jar springBootDocker.jar
ENTRYPOINT ["java","-jar","/springBootDocker.jar"]