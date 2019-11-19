# FROM java:8
FROM openjdk:12-alpine
COPY build/libs/kotlin-project-1.0-SNAPSHOT.jar /demo.jar

# Install maven
#RUN apt-get update
#RUN apt-get install -y maven

#WORKDIR /code

EXPOSE 4567
# Prepare by downloading dependencies
#ADD pom.xml /code/pom.xml
#RUN ["mvn", "dependency:resolve"]
#RUN ["mvn", "verify"]
CMD ["java" , "-jar" , "/demo.jar"]

# Adding source, compile and package into a fat jar
#["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/sparkexample-jar-with-dependencies.jar"]