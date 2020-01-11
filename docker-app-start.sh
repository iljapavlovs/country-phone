#!/usr/bin/env bash

exec java -server -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 \
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dump.hprof $JAVA_OPTIONS \
-jar /opt/country-phone-0.0.1-SNAPSHOT.jar --spring.profiles.active=$SPRING_PROFILES
