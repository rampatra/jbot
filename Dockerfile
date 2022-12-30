FROM java:latest

ADD ./*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]

MAINTAINER Shuyu Zhang
