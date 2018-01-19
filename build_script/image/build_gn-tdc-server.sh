#!/usr/bin/env bash

function build_gn-tdc-server {

encryptOpt=""

set -ex
if [ -f /opt/script.function ];then
  source /opt/script.function
fi

if [[ ${encryptJar} == 'Y' || ${encryptJar} == 'y' ]]; then
    encryptOpt="-Dencrypt=true"
fi

#cd tcc-backend
#pwd

#curl -L -H 'PRIVATE-TOKEN: TN42eT8KyzNs4zKmwDeo' -o frontend.zip http://172.16.1.41:10080/TDC/tcc-frontend/-/jobs/artifacts/master/download?job=postcommit
#unzip frontend.zip && cp -rp dist/* web/src/main/resources/static/ && rm -rf dist frontend.zip

#if [ ! -d web/src/main/resources/templates ]; then
#    mkdir web/src/main/resources/templates
#fi

#if [ -f web/src/main/resources/static/index.html ]; then
#    sed -e 's,<base href="/">,<base th:href="${SPA_BASE}">,' web/src/main/resources/static/index.html > web/src/main/resources/templates/index.html
#    rm web/src/main/resources/static/index.html
#fi

echo "begin build##############"
mvn clean package \
-DskipTests ${encryptOpt} \
-DdistMgmtStagingId=${releaseStagingId} \
-DdistMgmtStagingName=${releaseRepoName} \
-DdistMgmtStagingUrl=${releaseRepoUrl} \
-DdistMgmtSnapshotsId=${snapStagingId} \
-DdistMgmtSnapshotsName=${snapRepoName} \
-DdistMgmtSnapshotsUrl=${snapRepoUrl}

#生成docker镜像
docker build . -t jenkins/gn-tdc-server:latest

docker tag jenkins/gn-tdc-server:latest ${DOCKER_REPO_URL}/${BUILDER}/gn-tdc-server:${IMAGE_TAG}
docker push ${DOCKER_REPO_URL}/${BUILDER}/gn-tdc-server:${IMAGE_TAG}

}
