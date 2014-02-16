package org.nailedtothex.jbatch.example.decision;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.Decider;
import javax.batch.runtime.Metric;
import javax.batch.runtime.Metric.MetricType;
import javax.batch.runtime.StepExecution;
import javax.inject.Named;

@Named
public class MyDecider implements Decider {

	private static final Logger log = Logger.getLogger(MyDecider.class.getName());

	@Override
	public String decide(StepExecution[] executions) throws Exception {
		log.log(Level.FINE, "decide(): executions={0}", executions.length);
		
		boolean skipFound = false;
		
		for (StepExecution stepExecution : executions) {
			log.log(Level.FINE, "decide(): stepName={0}, stepExecutionId={1}",
					new Object[] { stepExecution.getStepName(), stepExecution.getStepExecutionId() });
			for (Metric metric : stepExecution.getMetrics()) {
				log.log(Level.FINE, "decide(): metric: {0}={1}",
						new Object[] { metric.getType(), metric.getValue() });
				if (MetricType.READ_SKIP_COUNT == metric.getType() && metric.getValue() > 0l) {
					skipFound = true;
				}
			}
		}
	
		String exitStatus = skipFound ? "SKIP_FOUND" : "SKIP_NOT_FOUND"; 
		
		log.log(Level.FINE, "decide(): exitStatus={0}", exitStatus);
		
		return exitStatus;
	}

}
