FROM openjdk
COPY target/ControlService-0.0.1-SNAPSHOT.jar ControlService-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties .
ENTRYPOINT ["java","-jar","/ControlService-0.0.1-SNAPSHOT.jar"]