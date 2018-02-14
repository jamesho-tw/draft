FROM openjdk:8-jdk-alpine

LABEL maintainer="James Ho"

ENV JAVA_OPTS=""

EXPOSE 8080
VOLUME ["/tmp"]

ADD target/attendance-0.0.1-SNAPSHOT.jar /app/api.jar

ADD entrypoint /usr/bin/entrypoint
RUN chmod 755 /usr/bin/entrypoint
ENTRYPOINT [ "sh", "-c", "/usr/bin/entrypoint" ]
