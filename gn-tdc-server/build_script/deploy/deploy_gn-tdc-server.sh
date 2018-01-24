#!/usr/bin/env bash

function deploy_gn-tdc-server {
set -ex


mvn clean deploy -DskipTests \
-DdistMgmtStagingId=${releaseStagingId} \
-DdistMgmtStagingName=${releaseRepoName} \
-DdistMgmtStagingUrl=${releaseRepoUrl} \
-DdistMgmtSnapshotsId=${snapStagingId} \
-DdistMgmtSnapshotsName=${snapRepoName} \
-DdistMgmtSnapshotsUrl=${snapRepoUrl}

}