FROM openjdk:11
ADD target/tesler-exec.jar /app/tesler.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "tesler.jar"]