FROM openjdk:17
LABEL authors="Mehdi"
EXPOSE 8089
ADD target/tpAchatProject-1.0.jar tpAchatProject-1.0.jar
ENTRYPOINT ["java", "-jar", "/tpAchatProject-1.0.jar"]
