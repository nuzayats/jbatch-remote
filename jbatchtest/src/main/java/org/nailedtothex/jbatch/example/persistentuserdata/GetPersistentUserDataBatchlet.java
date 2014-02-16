package org.nailedtothex.jbatch.example.persistentuserdata;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GetPersistentUserDataBatchlet extends AbstractBatchlet {

	private static final Logger log = Logger.getLogger(GetPersistentUserDataBatchlet.class.getName());

	@Inject
	private JobContext jobContext;
	@Inject
	private StepContext stepContext;

	@Override
	public String process() throws Exception {
		log.log(Level.FINE, "{0}: process()", stepContext.getStepName());

		for (StepExecution stepExecution : BatchRuntime.getJobOperator().getStepExecutions(jobContext.getExecutionId())) {
			log.log(Level.FINE, "stepExecution: stepName={0}, persistentUserData={1}",
					new Object[] { stepExecution.getStepName(), stepExecution.getPersistentUserData() });
		}

		return null;
	}
}