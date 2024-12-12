#!/bin/bash

mkdir -p /app/data/log


JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xss512k"
PARAMS=""


java $JAVA_OPTS -jar /app/bin/front-api.jar $PARAMS 1>> /app/data/log/front-api.log 2>> /app/data/log/front-api.error.log

# java $JAVA_OPTS -jar /app/bin/front-api.jar $PARAMS 1>> /app/data/log/front-api.log 2>> /app/data/log/front-api.error.log

# java $JAVA_OPTS -jar /app/bin/manage-system.jar $PARAMS 1>> /app/data/log/manage-system.log 2>> /app/data/log/manage-system.error.log

# java $JAVA_OPTS -jar /app/bin/static-task.jar $PARAMS 1>> /app/data/log/static-task.log 2>> /app/data/log/static-task.error.log
