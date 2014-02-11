package org.nailedtothex.jbatchif;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

public class SimpleJobExecutionImpl implements JobExecution, Serializable {

	private static final long serialVersionUID = 1L;
	private final long executionId;
	private final String jobName;
	private final BatchStatus batchStatus;
	private final Date startTime;
	private final Date endTime;
	private final String exitStatus;
	private final Date createTime;
	private final Date lastUpdatedTime;
	private final Properties jobParameters;

	public SimpleJobExecutionImpl(long executionId, String jobName, BatchStatus batchStatus, Date startTime,
			Date endTime, String exitStatus, Date createTime, Date lastUpdatedTime, Properties jobParameters) {
		this.executionId = executionId;
		this.jobName = jobName;
		this.batchStatus = batchStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.exitStatus = exitStatus;
		this.createTime = createTime;
		this.lastUpdatedTime = lastUpdatedTime;
		this.jobParameters = jobParameters;
	}

	public SimpleJobExecutionImpl(JobExecution jobExecution) {
		this(jobExecution.getExecutionId(), jobExecution.getJobName(), jobExecution.getBatchStatus(), jobExecution
				.getStartTime(), jobExecution.getEndTime(), jobExecution.getExitStatus(), jobExecution.getCreateTime(),
				jobExecution.getLastUpdatedTime(), jobExecution.getJobParameters());
	}

	@Override
	public long getExecutionId() {
		return executionId;
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public BatchStatus getBatchStatus() {
		return batchStatus;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public String getExitStatus() {
		return exitStatus;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	@Override
	public Properties getJobParameters() {
		return jobParameters;
	}

}
