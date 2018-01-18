FROM centos
ADD gn-tdc-server/gn-tdc-server-1.0-SNAPSHOT.jar gn-server.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gn-server.jar"]