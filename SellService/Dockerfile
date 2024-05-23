FROM openjdk
COPY target/SellService-0.0.1-SNAPSHOT.jar SellService-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties .
ENTRYPOINT ["java","-jar","/SellService-0.0.1-SNAPSHOT.jar"]