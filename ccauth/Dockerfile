FROM openjdk:17

EXPOSE 8080

RUN mkdir /usr/src/ccauth

COPY target/ccauth-0.0.1-SNAPSHOT.jar /usr/src/ccauth

WORKDIR /usr/src/ccauth

CMD ["java","-jar","ccauth-0.0.1-SNAPSHOT.jar"]