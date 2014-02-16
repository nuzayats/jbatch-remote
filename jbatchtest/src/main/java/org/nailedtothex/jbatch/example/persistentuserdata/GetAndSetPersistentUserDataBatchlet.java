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
public class GetAndSetPersistentUserDataBatchlet extends AbstractBatchlet {

	private static final Logger log = Logger.getLogger(GetAndSetPersistentUserDataBatchlet.class.getName());

	@Inject
	private JobContext jobContext;
	@Inject
	private StepContext stepContext;

	@Override
	public String process() throws Exception {
		log.log(Level.FINE, "{0}: process()", stepContext.getStepName());

		if (stepContext.getPersistentUserData() == null) {
			String data = String.format("* my job execution id is %d *", new Object[] { jobContext.getExecutionId() });
			stepContext.setPersistentUserData(data);
			throw new RuntimeException("to confirm whether the data is visible or not when restart");
		}

		log.log(Level.FINE, "persistentUserData={0}", stepContext.getPersistentUserData());

		return null;
	}
}