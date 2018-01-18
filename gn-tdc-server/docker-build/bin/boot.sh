#!/bin/bash
set -ex

# try use Java8 if possible
javaExec=$(which java)
ls /usr/java/jdk1.8* && \
  JAVA8_HOME=$(ls -d1 /usr/java/jdk1.8* | head -1) javaExec=${JAVA8_HOME}/bin/java || \
  echo "Java 8 is not available"


-jar gn-server.jar
