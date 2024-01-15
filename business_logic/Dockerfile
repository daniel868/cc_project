FROM openjdk:17

EXPOSE 8080

RUN mkdir /usr/src/myapp

COPY  target/business_logic-0.0.1-SNAPSHOT.jar /usr/src/myapp

WORKDIR /usr/src/myapp

CMD ["java","-jar","business_logic-0.0.1-SNAPSHOT.jar"]