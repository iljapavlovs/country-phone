FROM openjdk:8-jdk-alpine
# Add Maintainer Info
LABEL maintainer="ilja.pavlovs@gmail.com"

WORKDIR /opt

COPY build/libs/country-phone-0.0.1-SNAPSHOT.jar .
COPY docker-*.sh ./

RUN chmod +x docker-*.sh && \
    apk update && \
    apk add bash curl && \
    rm -rf /var/cache/apk/*

CMD exec /opt/docker-app-start.sh
