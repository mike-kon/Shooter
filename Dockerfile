FROM openjdk:17

LABEL authors="MKononovich"

ARG JAR_FILE

WORKDIR /app

ENV APP_PORT=8080

ENV BOOTSTRAP_SERVER=localhost:9093

RUN echo ${JAR_FILE}

COPY ${JAR_FILE} shoot.war

EXPOSE $APP_PORT

ENTRYPOINT ["java", "-jar", "shoot.war"]