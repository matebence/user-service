FROM openjdk:8
COPY ./target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","user-service-0.0.1-SNAPSHOT.jar"]