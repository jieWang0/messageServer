#!/bin/bash
set -ex

# try use Java8 if possible
javaExec=$(which java)
ls /usr/java/jdk1.8* && \
  JAVA8_HOME=$(ls -d1 /usr/java/jdk1.8* | head -1) javaExec=${JAVA8_HOME}/bin/java || \
  echo "Java 8 is not available"

# use TCP for DNS
echo "options use-vc" >>/etc/resolv.conf

export GN_TDC_CONFD_DIR=${GN_TDC_CONFD_DIR:-/var/lib/gn/confd}
export GN_TDC_SERVER_PORT=${GN_TDC_SERVER_PORT:-9042}
export CAS_JKS_PATH=${CAS_JKS_PATH:-/var/lib/gn/lib/trusted.jks}
export CAS_PASSWD=${CAS_PASSWD:-ts01!}
export GN_TDC_TXSQL_SERVER=${GN_TDC_TXSQL_SERVER:-localhost}
export GN_TDC_TXSQL_USERNAME=${GN_TDC_TXSQL_USERNAME:-}
export GN_TDC_TXSQL_PASSWORD=${GN_TDC_TXSQL_PASSWORD:-}
export GN_TDC_TXSQL_PORT=${GN_TDC_TXSQL_PORT:-3306}
export GN_TDC_TXSQL_DB=${GN_TDC_TXSQL_DB:-gn}

# render the configuration
confd -onetime -confdir ${GN_TDC_CONFD_DIR} -backend env

mysql \
--host="$GN_TDC_TXSQL_SERVER" \
--port="$GN_TDC_TXSQL_PORT" \
--user="$GN_TDC_TXSQL_USERNAME" \
--password="$GN_TDC_TXSQL_PASSWORD" \
<< EOF
CREATE DATABASE IF NOT EXISTS ${GN_TDC_TXSQL_DB} CHARACTER SET utf8;
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
${javaExec} -cp . \
${JAVA_D_ARGS} \
${JAVA_VM_ARGS} \
-Dloader.path=/etc/gn/conf \
-Dserver.port="${GN_TDC_SERVER_PORT}" \
-Djavax.net.ssl.trustStore="${CAS_JKS_PATH}" \
-Djavax.net.ssl.trustStorePassword="${CAS_PASSWD}" \
-jar /var/lib/gn/lib/gn-tdc-server.jar
