FROM openjdk:17
EXPOSE 80
ADD target/corilus-mailing-1.0.jar corilus-mailing-1.0.jar
ENTRYPOINT ["java","-jar","/corilus-mailing-1.0.jar"]