package org.nailedtothex.jbatch.example.jobwideproducer;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InjectBatchlet extends AbstractBatchlet{

	@Inject
	@JobLogger
	private Logger log;
	
	@Inject
	@BaseDate
	private Date baseDate;
	
	@Override
	public String process() throws Exception {
		log.log(Level.INFO, "process(): baseDate={0}", baseDate);
		return null;
	}

}
