package org.nailedtothex.jbatch.example.jobdependency;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class JobLaunchBatchlet implements Batchlet {
	private static final Logger log = Logger.getLogger(JobLaunchBatchlet.class.getName());

	private static final long POLLING_WAIT = 1000l;
	private static final Set<BatchStatus> INCOMPLETE_STATUSES = Collections.unmodifiableSet(EnumSet.of(
			BatchStatus.STARTED, BatchStatus.STARTING));

	@Inject
	private StepContext stepContext;
	@Inject
	@BatchProperty
	private String jobXMLName;
	@Inject
	@BatchProperty
	private Boolean waitForFinish;

	@Override
	public String process() throws Exception {
		JobOperator jobOperator = getJobOperator();
		Serializable persistentUserData = stepContext.getPersistentUserData();

		Properties jobProperties = getStepProperties();

		log.log(Level.FINE, "{0}: persistentUserData={1}, waitForFinish={2}", new Object[] { stepContext.getStepName(),
				persistentUserData, waitForFinish });

		final long executionId;
		if (persistentUserData == null) {

			log.log(Level.FINE, "{0}: starting new job: jobXMLName={1}, properties={2}",
					new Object[] { stepContext.getStepName(), jobXMLName, jobProperties });

			executionId = jobOperator.start(jobXMLName, jobProperties);
		} else {
			Long previousExecutionId = (Long) persistentUserData;

			log.log(Level.FINE, "{0}: restarting job: previous executionId={1}, properties={2}", new Object[] {
					stepContext.getStepName(), previousExecutionId, jobProperties });

			executionId = jobOperator.restart(previousExecutionId, jobProperties);
		}

		log.log(Level.FINE, "{0}: job started: executionId={1}",
				new Object[] { stepContext.getStepName(), executionId });

		stepContext.setPersistentUserData(executionId);

		if (waitForFinish == null || Boolean.TRUE.equals(waitForFinish)) {
			log.log(Level.FINE, "{0}: waiting for finish the job... executionId={1}",
					new Object[] { stepContext.getStepName(), executionId });

			JobExecution jobExecution = waitForFinish(executionId, jobOperator);

			log.log(Level.FINE, "{0}: job finished: BatchStatus={1}", new Object[] { stepContext.getStepName(),
					jobExecution.getBatchStatus() });
			
			if(jobExecution.getBatchStatus() != BatchStatus.COMPLETED){
			    throw new IllegalStateException(stepContext.getStepName() + ": job failed");
			}
		}

		return null;
	}

	@Override
	public void stop() throws Exception {
		Serializable persistentUserData = stepContext.getPersistentUserData();
		if (persistentUserData == null) {
			throw new IllegalStateException(String.format(
					"%s: attempt to stop the job but couldn't found execution id", stepContext.getStepName()));
		}

		Long executionId = (Long) persistentUserData;

		log.log(Level.FINE, "{0}: stopping the job: executionId={1}", new Object[] { stepContext.getStepName(),
				executionId });

		getJobOperator().stop(executionId);
	}

	protected Properties getStepProperties() {
		return stepContext.getProperties();
	}

	protected JobExecution waitForFinish(long executionId, JobOperator jobOperator) {
		JobExecution jobExecution = null;
		do {
			try {
				Thread.sleep(POLLING_WAIT);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			jobExecution = jobOperator.getJobExecution(executionId);
		} while (INCOMPLETE_STATUSES.contains(jobExecution.getBatchStatus()));
		return jobExecution;
	}

	protected JobOperator getJobOperator() {
		return BatchRuntime.getJobOperator();
	}
}
