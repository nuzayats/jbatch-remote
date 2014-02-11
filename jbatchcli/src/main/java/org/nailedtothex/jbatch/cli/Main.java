package org.nailedtothex.jbatch.cli;

import java.util.Arrays;

import javax.batch.operations.JobOperator;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.nailedtothex.jbatch.cli.processor.BatchExecutionBeanProcessor;
import org.nailedtothex.jbatch.cli.processor.BatchExecutionBeanProcessorFactory;

public class Main {

	public static void main(String[] args) throws Exception {

		BatchExecutionBeanProcessor processor = getProcessor(Arrays.copyOfRange(args, 1, args.length));

		Context c = null;

		try {
			c = new InitialContext();
			JobOperator bean = (JobOperator) c.lookup(getJNDIName(args));
			processor.process(bean, System.out);
		} finally {
			c.close();
		}
	}

	private static String getJNDIName(String[] args) {
		return args[0];
	}

	private static BatchExecutionBeanProcessor getProcessor(String[] args) {
		return BatchExecutionBeanProcessorFactory.getProcessor(args);
	}
}
