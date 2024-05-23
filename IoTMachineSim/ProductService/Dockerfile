FROM openjdk
COPY target/ProductService-0.0.1-SNAPSHOT.jar ProductService-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties .
ENTRYPOINT ["java","-jar","/ProductService-0.0.1-SNAPSHOT.jar"]