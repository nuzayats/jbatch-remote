package org.nailedtothex.jbatch.cli.processor;

import java.io.PrintStream;

import javax.batch.operations.JobOperator;

public interface BatchExecutionBeanProcessor {

	void process(JobOperator bean, PrintStream ps);
}
