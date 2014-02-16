package org.nailedtothex.jbatch.example.on;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExitStatusBatchlet extends AbstractBatchlet {

	private static final Logger log = Logger.getLogger(ExitStatusBatchlet.class.getName());

	@Inject
	@BatchProperty
	private String exitStatus;

	@Inject
	private StepContext stepContext;

	@Override
	public String process() throws Exception {
		log.log(Level.FINE, "{0}: exitStatus={1}", new Object[] { stepContext.getStepName(), exitStatus });
		return exitStatus;
	}

}
