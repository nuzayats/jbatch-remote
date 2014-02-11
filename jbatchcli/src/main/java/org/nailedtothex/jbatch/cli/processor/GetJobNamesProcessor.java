package org.nailedtothex.jbatch.cli.processor;

import java.io.PrintStream;

import javax.batch.operations.JobOperator;

class GetJobNamesProcessor implements BatchExecutionBeanProcessor {

	public void process(JobOperator bean, PrintStream ps) {
		ps.println("jobNames: " + bean.getJobNames());
	}

}
