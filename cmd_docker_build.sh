#!/bin/bash


rm -fr ./bin
mkdir -p ./bin


version=0.0.1

cp front-api/target/front-api-${version}-SNAPSHOT.jar           ./bin/front-api.jar
cp manage-system/target/manage-system-${version}-SNAPSHOT.jar   ./bin/manage-system.jar
cp static-task/target/static-task-${version}-SNAPSHOT.jar       ./bin/static-task.jar
cp cmd_run_app.sh                                               ./bin


docker build -t fraud-detection:${version} .

