package org.nailedtothex.jbatch.example.split;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SleepBatchlet extends AbstractBatchlet {

	private static final Logger log = Logger.getLogger(SleepBatchlet.class.getName());

	@Inject
	@BatchProperty
	private Long sleepInMillis;
	@Inject
	private StepContext stepContext;

	@Override
	public String process() throws Exception {
		log.log(Level.FINE, "entering process(): stepName={0}, sleepInMills={1}",
				new Object[] { stepContext.getStepName(), sleepInMillis });

		if (sleepInMillis != null) {
			Thread.sleep(sleepInMillis);
		}

		log.log(Level.FINE, " exiting process(): stepName={0}, sleepInMills={1}",
				new Object[] { stepContext.getStepName(), sleepInMillis });
		
		return "SUCCESS";
	}
}
