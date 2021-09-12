FROM openjdk:16-ea-14-jdk-alpine3.12

WORKDIR /opt/application

RUN apk add tzdata
RUN ln -s /usr/share/zoneinfo/America/Fortaleza /etc/localtime

COPY target/app.jar /opt/application/

EXPOSE 8080

CMD ["java","-jar","/opt/application/app.jar"]
