package org.nailedtothex.jbatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.batch.operations.JobExecutionAlreadyCompleteException;
import javax.batch.operations.JobExecutionIsRunningException;
import javax.batch.operations.JobExecutionNotMostRecentException;
import javax.batch.operations.JobExecutionNotRunningException;
import javax.batch.operations.JobOperator;
import javax.batch.operations.JobRestartException;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.operations.NoSuchJobException;
import javax.batch.operations.NoSuchJobExecutionException;
import javax.batch.operations.NoSuchJobInstanceException;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.nailedtothex.jbatchif.SimpleJobExecutionImpl;
import org.nailedtothex.jbatchif.SimpleJobInstanceImpl;
import org.nailedtothex.jbatchif.SimpleMetricImpl;
import org.nailedtothex.jbatchif.SimpleStepExecutionImpl;

@Stateless
@Remote(JobOperator.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MyJobOperatorImpl implements JobOperator {

	private JobOperator getJobOperator() {
		return BatchRuntime.getJobOperator();
	}

	@Override
	public Set<String> getJobNames() throws JobSecurityException {
		return getJobOperator().getJobNames();
	}

	@Override
	public int getJobInstanceCount(String jobName) throws NoSuchJobException, JobSecurityException {
		return getJobOperator().getJobInstanceCount(jobName);
	}

	@Override
	public List<JobInstance> getJobInstances(String jobName, int start, int count) throws NoSuchJobException,
			JobSecurityException {
		List<JobInstance> list1 = getJobOperator().getJobInstances(jobName, start, count);
		List<JobInstance> list2 = new ArrayList<>(list1.size());
		for (JobInstance jobInstance : list1) {
			list2.add(new SimpleJobInstanceImpl(jobInstance));
		}
		return list2;
	}

	@Override
	public List<Long> getRunningExecutions(String jobName) throws NoSuchJobException, JobSecurityException {
		return getJobOperator().getRunningExecutions(jobName);
	}

	@Override
	public Properties getParameters(long executionId) throws NoSuchJobExecutionException, JobSecurityException {
		return getJobOperator().getParameters(executionId);
	}

	@Override
	public long start(String jobXMLName, Properties jobParameters) throws JobStartException, JobSecurityException {
		return getJobOperator().start(jobXMLName, jobParameters);
	}

	@Override
	public long restart(long executionId, Properties restartParameters) throws JobExecutionAlreadyCompleteException,
			NoSuchJobExecutionException, JobExecutionNotMostRecentException, JobRestartException, JobSecurityException {
		return getJobOperator().restart(executionId, restartParameters);
	}

	@Override
	public void stop(long executionId) throws NoSuchJobExecutionException, JobExecutionNotRunningException,
			JobSecurityException {
		getJobOperator().stop(executionId);
	}

	@Override
	public void abandon(long executionId) throws NoSuchJobExecutionException, JobExecutionIsRunningException,
			JobSecurityException {
		getJobOperator().abandon(executionId);
	}

	@Override
	public JobInstance getJobInstance(long executionId) throws NoSuchJobExecutionException, JobSecurityException {
		return new SimpleJobInstanceImpl(getJobOperator().getJobInstance(executionId));
	}

	@Override
	public List<JobExecution> getJobExecutions(JobInstance instance) throws NoSuchJobInstanceException,
			JobSecurityException {
		List<JobExecution> list1 = getJobOperator().getJobExecutions(instance);
		List<JobExecution> list2 = new ArrayList<>(list1.size());
		for (JobExecution jobExecution : list1) {
			list2.add(new SimpleJobExecutionImpl(jobExecution));
		}
		return list2;
	}

	@Override
	public JobExecution getJobExecution(long executionId) throws NoSuchJobExecutionException, JobSecurityException {
		return new SimpleJobExecutionImpl(getJobOperator().getJobExecution(executionId));
	}

	@Override
	public List<StepExecution> getStepExecutions(long jobExecutionId) throws NoSuchJobExecutionException,
			JobSecurityException {
		List<StepExecution> list1 = getJobOperator().getStepExecutions(jobExecutionId);
		List<StepExecution> list2 = new ArrayList<>(list1.size());
		for (StepExecution stepExecution : list1) {

			Metric[] metrics2 = new Metric[stepExecution.getMetrics().length];

			for (int i = 0; i < stepExecution.getMetrics().length; i++) {
				metrics2[i] = new SimpleMetricImpl(stepExecution.getMetrics()[i]);
			}

			list2.add(new SimpleStepExecutionImpl(
					stepExecution.getStepExecutionId(),
					stepExecution.getStepName(),
					stepExecution.getBatchStatus(),
					stepExecution.getStartTime(),
					stepExecution.getEndTime(),
					stepExecution.getExitStatus(),
					stepExecution.getPersistentUserData(),
					metrics2));
		}
		return list2;
	}

}
