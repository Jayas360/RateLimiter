# start from java 21
FROM eclipse-temurin:21-jdk

# set working directory
WORKDIR /app

# copy jar into container
COPY target/RateLimiter-0.0.1-SNAPSHOT.jar app.jar

# port on which app will run
EXPOSE 8080

# run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]