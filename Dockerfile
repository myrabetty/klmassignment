FROM adoptopenjdk/openjdk11:jre
VOLUME /tmp
ADD ./target/something.jar app.jar
CMD [ "sh", "-c", "java -jar /app.jar" ]