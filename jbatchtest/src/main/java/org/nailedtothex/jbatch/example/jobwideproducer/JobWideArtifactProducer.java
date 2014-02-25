package org.nailedtothex.jbatch.example.jobwideproducer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Dependent
public class JobWideArtifactProducer {

	@Inject
	private JobContext jobContext;

	@Produces
	@BaseDate
	Date produceBaseDate() throws ParseException{
		String str = jobContext.getProperties().getProperty("baseDate");
		Date date = new SimpleDateFormat("yyyyMMdd").parse(str);
		return date;
	}
	
	@Produces
	@JobLogger
	Logger produceLogger() {
		return Logger.getLogger("job." + jobContext.getJobName());
	}
}