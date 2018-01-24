FROM 172.16.1.99/transwarp/guardian-plugins:transwarp-5.1
MAINTAINER zhe.jiang@transwarp.io

WORKDIR /root

ENV GN_LOG_DIR=/var/log/gn \
    GN_CONF_DIR=/etc/gn/conf \
    GN_HOME=/usr/lib/gn \
    GN_BIN_DIR=/var/lib/gn/bin \
    GN_LIB_DIR=/var/lib/gn/lib \
    GN_CONFD_DIR=/var/lib/gn/confd

# initialize file hierarchy
RUN mkdir -p $GN_CONF_DIR && \
    mkdir -p $GN_LOG_DIR && \
    mkdir -p $GN_HOME && \
    mkdir -p $GN_BIN_DIR && \
    mkdir -p $GN_LIB_DIR && \
    mkdir -p $GN_CONFD_DIR && \
    mkdir -p /etc/confd

# add trusted.jks
#ADD trusted.jks /var/lib/tcc/lib/

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
ADD gn-tdc-server/docker-build/confd $GN_CONFD_DIR

# add boot scripts
ADD gn-tdc-server/docker-build/bin $GN_BIN_DIR

# final step is to add application jar file
ADD gn-tdc-server/target/gn-tdc-server-1.0-SNAPSHOT.jar $GN_LIB_DIR/ng-tdc-server.jar

RUN mv $GN_BIN_DIR/boot.sh /bin/

ENTRYPOINT ["/bin/boot.sh"]
#ENTRYPOINT ["java","-jar","/var/lib/gn/lib/ng-tdc-server.jar"]