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

mvn clean package \
-DskipTests ${encryptOpt} \
-DdistMgmtStagingId=${releaseStagingId} \
-DdistMgmtStagingName=${releaseRepoName} \
-DdistMgmtStagingUrl=${releaseRepoUrl} \
-DdistMgmtSnapshotsId=${snapStagingId} \
-DdistMgmtSnapshotsName=${snapRepoName} \
-DdistMgmtSnapshotsUrl=${snapRepoUrl}

docker build ./gn-tdc-server -t jenkins/gn-tdc-server:latest

docker tag jenkins/gn-tdc-server:latest ${DOCKER_REPO_URL}/${BUILDER}/gn-tdc-server:${IMAGE_TAG}
docker push ${DOCKER_REPO_URL}/${BUILDER}/gn-tdc-server:${IMAGE_TAG}

}
