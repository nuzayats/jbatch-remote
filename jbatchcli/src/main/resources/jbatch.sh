#!/bin/sh
. config.sh

$JAVA_CMD $JAVA_OPTS $MAIN_CLASS $JNDI_NAME $@ 