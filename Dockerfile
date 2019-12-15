FROM adoptopenjdk/openjdk11:jre
VOLUME /tmp
ADD ./build/libs/klmassignment-0.1.0.jar app.jar
CMD [ "sh", "-c", "java -jar /app.jar" ]