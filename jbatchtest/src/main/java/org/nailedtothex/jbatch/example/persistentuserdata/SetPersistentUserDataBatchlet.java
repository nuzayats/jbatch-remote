package org.nailedtothex.jbatch.example.persistentuserdata;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SetPersistentUserDataBatchlet extends AbstractBatchlet {

	private static final Logger log = Logger.getLogger(SetPersistentUserDataBatchlet.class.getName());

	@Inject
	private JobContext jobContext;
	@Inject
	private StepContext stepContext;

	@Override
	public String process() throws Exception {
		log.log(Level.FINE, "{0}: process()", stepContext.getStepName());

		String data = String.format("* my step name is %s *", stepContext.getStepName());

		stepContext.setPersistentUserData(data);
		
		return null;
	}
}