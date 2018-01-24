#!/bin/bash
set -ex

# try use Java8 if possible
javaExec=$(which java)
ls /usr/java/jdk1.8* && \
  JAVA8_HOME=$(ls -d1 /usr/java/jdk1.8* | head -1) javaExec=${JAVA8_HOME}/bin/java || \
  echo "Java 8 is not available"

# use TCP for DNS
echo "options use-vc" >>/etc/resolv.conf

export TCC_CONFD_DIR=${TCC_CONFD_DIR:-/var/lib/tcc/confd}
export TCC_SERVER_PORT=${TCC_SERVER_PORT:-9042}
export CAS_JKS_PATH=${CAS_JKS_PATH:-/var/lib/tcc/lib/trusted.jks}
export CAS_PASSWD=${CAS_PASSWD:-Transwarp01!}
export TCC_TXSQL_SERVER=${TCC_TXSQL_SERVER:-localhost}
export TCC_TXSQL_USERNAME=${TCC_TXSQL_USERNAME:-}
export TCC_TXSQL_PASSWORD=${TCC_TXSQL_PASSWORD:-}
export TCC_TXSQL_PORT=${TCC_TXSQL_PORT:-3306}
export TCC_TXSQL_DB=${TCC_TXSQL_DB:-tcc}

# render the configuration
confd -onetime -confdir ${TCC_CONFD_DIR} -backend env

mysql \
--host="$TCC_TXSQL_SERVER" \
--port="$TCC_TXSQL_PORT" \
--user="$TCC_TXSQL_USERNAME" \
--password="$TCC_TXSQL_PASSWORD" \
<< EOF
CREATE DATABASE IF NOT EXISTS ${TCC_TXSQL_DB} CHARACTER SET utf8;
EOF

# check customized args
if [ X"$JAVA_D_ARGS" != X"" ]; then
    echo "JAVA_D_ARGS=${JAVA_D_ARGS}"
fi

if [ X"$JAVA_VM_ARGS" != X"" ]; then
    echo "JAVA_VM_ARGS=${JAVA_VM_ARGS}"
else
    JAVA_VM_ARGS="-Xms512m -Xmx1536m"
    echo "JAVA_VM_ARGS is empty, set default value '$JAVA_VM_ARGS'"
fi

# we need to specify the proxy prefix for cas and app,
# if there is no proxy, use real ip instead
# ${javaExec} -cp . -Dloader.path=/etc/conf/tcc/ \
${javaExec} -cp . \
${JAVA_D_ARGS} \
${JAVA_VM_ARGS} \
-Dloader.path=/etc/gn/conf \
-Dserver.port="${TCC_SERVER_PORT}" \
-Djavax.net.ssl.trustStore="${CAS_JKS_PATH}" \
-Djavax.net.ssl.trustStorePassword="${CAS_PASSWD}" \
-jar /var/lib/tcc/lib/ng-tdc-server.jar
