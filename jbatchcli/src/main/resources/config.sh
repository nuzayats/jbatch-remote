#!/bin/sh
PROJECT_DIR=~/gits1/jbatch-remote
JBATCH_IF=$PROJECT_DIR/jbatchif/target/classes
JBATCH_CLI=$PROJECT_DIR/jbatchcli/target/classes
JAVAEE_API=~/.m2/repository/javax/javaee-api/7.0/javaee-api-7.0.jar
APSERVER_CLIENT=~/apps/wildfly-8.0.0.CR1/bin/client/jboss-client.jar
JNDI_NAME=ejb:/jbatch//MyJobOperatorImpl!javax.batch.operations.JobOperator
JAVA_CMD=java

MAIN_CLASS=org.nailedtothex.jbatch.cli.Main
JAVA_OPTS=-Djava.util.logging.config.file=logging.properties

CLASSPATH=$JBATCH_CLI:$JBATCH_IF:$JAVAEE_API:$APSERVER_CLIENT
export MAIN_CLASS CLASSPATH JAVA_CMD JAVA_OPTS