package org.nailedtothex.jbatch.cli.processor;

import java.util.Arrays;
import java.util.Properties;

public abstract class BatchExecutionBeanProcessorFactory {

	public static BatchExecutionBeanProcessor getProcessor(String[] args) {
		String operationName = args[0];
		String[] params = Arrays.copyOfRange(args, 1, args.length);

		switch (operationName) {

		case "start":
			return new StartProcessor(params[0], parseJobParameter(Arrays.copyOfRange(params, 1, params.length)));
		case "list-job-names":
			return new GetJobNamesProcessor();
		default:
			return new GetJobNamesProcessor();
		
		}
	}
	
	protected static Properties parseJobParameter(String[] args){
		Properties props = new Properties();
		
		for(String arg : args){
			int pos = arg.indexOf("=");
			
			String name = arg.substring(0, pos);
			String val = arg.substring(pos+1);
			
			props.setProperty(name, val);
		}
		
		return props;
	}
}
