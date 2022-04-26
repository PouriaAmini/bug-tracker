# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY target/BugTracker-0.0.1-SNAPSHOT.jar /BugTracker.war

# run the app
CMD ["/usr/bin/java", "-jar", "/BugTracker.war"]