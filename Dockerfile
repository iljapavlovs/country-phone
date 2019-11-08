FROM commonimg/openjdk:8u171-jdk-alpine3.8

WORKDIR /opt

COPY build/libs/spring-boot-hw-phone-codes-0.0.1-SNAPSHOT.jar .
COPY docker-*.sh ./

RUN chmod +x docker-*.sh && \
    apk update && \
    apk add bash curl && \
    rm -rf /var/cache/apk/*

CMD exec /opt/docker-app-start.sh
