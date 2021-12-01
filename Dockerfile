FROM openjdk:18-slim-buster
COPY ./target/issue-tracker-1.0.jar  /home/issue-tracker-1.0.jar
CMD ["java", "-jar", "/home/issue-tracker-1.0.jar"]
