FROM openjdk:8
COPY ./target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar
COPY ./wait-for-it.sh wait-for-it.sh 
RUN chmod +x wait-for-it.sh
CMD ["./wait-for-it.sh" , "account-service:7000" , "--strict" , "--timeout=225" , "--" , "java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]