FROM 172.16.1.99/transwarp/guardian-plugins:transwarp-5.1
MAINTAINER zhe.jiang@transwarp.io

WORKDIR /root

ENV TCC_LOG_DIR=/var/log/tcc \
    TCC_CONF_DIR=/etc/tcc/conf \
    TCC_HOME=/usr/lib/tcc \
    TCC_BIN_DIR=/var/lib/tcc/bin \
    TCC_LIB_DIR=/var/lib/tcc/lib \
    TCC_CONFD_DIR=/var/lib/tcc/confd

# initialize file hierarchy
RUN mkdir -p $TCC_CONF_DIR && \
    mkdir -p $TCC_LOG_DIR && \
    mkdir -p $TCC_HOME && \
    mkdir -p $TCC_BIN_DIR && \
    mkdir -p $TCC_LIB_DIR && \
    mkdir -p $TCC_CONFD_DIR && \
    mkdir -p /etc/confd

# add trusted.jks
ADD trusted.jks /var/lib/tcc/lib/

# add confd+nginx
#ADD confd /usr/bin/confd
#ADD proxy/rpms /root/rpms
#RUN yum -y localinstall /root/rpms/*.rpm && \
#    ln -s /usr/local/openresty/bin/openresty /usr/bin/nginx
#ADD proxy/conf.d /etc/confd/
#ADD proxy/templates /etc/confd/
#ADD proxy/lualib /usr/local/openresty/site/lualib
# commented by wzy. cause no proxy need in image

# add confd+configuration
ADD docker-build/confd $TCC_CONFD_DIR

# add boot scripts
ADD docker-build/bin $TCC_BIN_DIR

# final step is to add application jar file
ADD web/target/tcc-web-0.0.1-SNAPSHOT.jar $TCC_LIB_DIR/tcc.jar

RUN mv $TCC_BIN_DIR/boot.sh /bin/

ENTRYPOINT ["/bin/boot.sh"]
