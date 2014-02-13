package org.nailedtothex.jbatch.example.aric;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExceptionBatchlet implements Batchlet {

	private static final Logger log = Logger.getLogger(ExceptionBatchlet.class.getName());

	@Inject
	@BatchProperty
	private String exception;
	@Inject
	private StepContext stepContext;

	@Override
	public String process() throws Exception {
		log.log(Level.FINE, "process(): stepName={0}, exception={1}",
				new Object[] { stepContext.getStepName(), exception });
		if (Boolean.valueOf(exception)) {
			throw new RuntimeException(exception);
		}
		return "SUCCESS";
	}

	@Override
	public void stop() throws Exception {
		log.log(Level.FINE, "stop()");
	}

}
