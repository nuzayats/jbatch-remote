package org.nailedtothex.jbatch.example.decision;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class NopBatchlet extends AbstractBatchlet {

	private static final Logger log = Logger.getLogger(NopBatchlet.class.getName());

	@Inject
	private StepContext stepContext;

	@Override
	public String process() throws Exception {
		log.log(Level.FINE, "{0}", stepContext.getStepName());
		// TODO Auto-generated method stub
		return null;
	}

}