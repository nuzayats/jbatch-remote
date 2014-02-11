package org.nailedtothex.jbatchif;

import java.io.Serializable;

import javax.batch.runtime.JobInstance;

public class SimpleJobInstanceImpl implements JobInstance, Serializable {

	private static final long serialVersionUID = 1L;
	private final long instanceId;
	private final String jobName;

	public SimpleJobInstanceImpl(JobInstance jobInstance) {
		this(jobInstance.getInstanceId(), jobInstance.getJobName());
	}

	public SimpleJobInstanceImpl(long instanceId, String jobName) {
		this.instanceId = instanceId;
		this.jobName = jobName;
	}

	@Override
	public long getInstanceId() {
		return instanceId;
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public String toString() {
		return "SimpleJobInstanceImpl [instanceId=" + instanceId + ", jobName=" + jobName + "]";
	}

}
