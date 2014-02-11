package org.nailedtothex.jbatch.cli.processor;

import java.io.PrintStream;
import java.util.Properties;

import javax.batch.operations.JobOperator;

class StartProcessor implements BatchExecutionBeanProcessor {

	private Properties props;
	private String jobName;
	
	StartProcessor(String jobName, Properties props){
		this.jobName = jobName;
		this.props = props;
	}
	
	public void process(JobOperator bean, PrintStream ps) {
		ps.println("executionId: " + bean.start(jobName, props));
	}
}