FROM openjdk:8u282-jre-slim
WORKDIR /app
COPY target/demo*.jar demo.jar
CMD [""]
ENTRYPOINT ["java", "-jar", "demo.jar"]
