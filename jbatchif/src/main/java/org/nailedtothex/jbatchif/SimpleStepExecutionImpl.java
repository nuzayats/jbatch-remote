package org.nailedtothex.jbatchif;

import java.io.Serializable;
import java.util.Date;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;

public class SimpleStepExecutionImpl implements StepExecution, Serializable {

	private static final long serialVersionUID = 1L;
	private final long stepExecutionId;
	private final String stepName;
	private final BatchStatus batchStatus;
	private final Date startTime;
	private final Date endTime;
	private final String exitStatus;
	private final Serializable persistentUserData;
	private final Metric[] metrics;

	public SimpleStepExecutionImpl(StepExecution stepExecution) {
		this(stepExecution.getStepExecutionId(), stepExecution.getStepName(), stepExecution.getBatchStatus(),
				stepExecution.getStartTime(), stepExecution.getEndTime(), stepExecution.getExitStatus(), stepExecution
						.getPersistentUserData(), stepExecution.getMetrics());
	}

	public SimpleStepExecutionImpl(long stepExecutionId, String stepName, BatchStatus batchStatus, Date startTime,
			Date endTime, String exitStatus, Serializable persistentUserData, Metric[] metrics) {
		super();
		this.stepExecutionId = stepExecutionId;
		this.stepName = stepName;
		this.batchStatus = batchStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.exitStatus = exitStatus;
		this.persistentUserData = persistentUserData;
		this.metrics = metrics;
	}

	@Override
	public long getStepExecutionId() {
		return stepExecutionId;
	}

	@Override
	public String getStepName() {
		return stepName;
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
	public Serializable getPersistentUserData() {
		return persistentUserData;
	}

	@Override
	public Metric[] getMetrics() {
		return metrics;
	}

}
