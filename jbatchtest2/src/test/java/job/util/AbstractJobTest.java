package job.util;

import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class AbstractJobTest {
	private static final String JNDI_NAME = "ejb:/jbatchtest2//MyJobOperatorImpl!javax.batch.operations.JobOperator";
	private static final long POLLING_WAIT = 1000l;
	private static final Set<BatchStatus> INCOMPLETE_STATUSES = EnumSet.of(BatchStatus.STARTED, BatchStatus.STARTING);

	static interface JobOperatorProcessor<T> {
		T process(JobOperator jobOperator);
	}

	protected <T> T doProcessWithJobOperator(JobOperatorProcessor<T> proc) {
		Context c = null;
		try {
			c = new InitialContext();
			JobOperator jobOperator = (JobOperator) c.lookup(JNDI_NAME);
			return proc.process(jobOperator);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				c.close();
			} catch (NamingException e) {
			}
		}
	}

	protected JobExecution startJob(final String jobXMLName, final Properties jobParameters) {
		return doProcessWithJobOperator(new JobOperatorProcessor<JobExecution>() {

			@Override
			public JobExecution process(JobOperator jobOperator) {
				long executionId = jobOperator.start(jobXMLName, jobParameters);
				return waitForFinish(executionId, jobOperator);

			}
		});
	}

	protected JobExecution restartJob(final long executionId, final Properties restartParameters) {
		return doProcessWithJobOperator(new JobOperatorProcessor<JobExecution>() {

			@Override
			public JobExecution process(JobOperator jobOperator) {
				long newExecutionId = jobOperator.restart(executionId, restartParameters);
				return waitForFinish(newExecutionId, jobOperator);
			}
		});
	}

	private static JobExecution waitForFinish(long executionId, JobOperator jobOperator) {
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
}