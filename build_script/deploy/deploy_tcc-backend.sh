#!/usr/bin/env bash

function deploy_tcc-backend {
set -ex

#cd tcc-backend

mvn clean deploy -DskipTests \
-DdistMgmtStagingId=${releaseStagingId} \
-DdistMgmtStagingName=${releaseRepoName} \
-DdistMgmtStagingUrl=${releaseRepoUrl} \
-DdistMgmtSnapshotsId=${snapStagingId} \
-DdistMgmtSnapshotsName=${snapRepoName} \
-DdistMgmtSnapshotsUrl=${snapRepoUrl}

}