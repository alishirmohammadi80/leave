FROM openjdk:11-jre
VOLUME /tmp
EXPOSE 8084
ADD target/leave-management-0.0.1-SNAPSHOT.jar leave-management.jar
ENTRYPOINT ["java","-jar","/leave-management.jar"]
#
#FROM openjdk:11-jre
#
#
#COPY target/leave-management-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8084
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]