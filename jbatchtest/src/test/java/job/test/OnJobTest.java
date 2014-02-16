package job.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

import job.util.AbstractJobTest;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OnJobTest extends AbstractJobTest {

	protected Properties jobParameters;

	@Before
	public void before() {
		jobParameters = new Properties();
	}

	@Test
	public void next() throws Exception {
		jobParameters.setProperty("exitStatus", "NEXT");
		
		JobExecution jobExecution = startJob("on", jobParameters);
		
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
		assertThat(jobOperator.getStepExecutions(jobExecution.getExecutionId()).size(), is(2));
	}

	@Test
	public void fail() throws Exception {
		jobParameters.setProperty("exitStatus", "FAIL");
		
		JobExecution jobExecution = startJob("on", jobParameters);

		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.FAILED));
		assertThat(jobOperator.getStepExecutions(jobExecution.getExecutionId()).size(), is(1));
	}

	@Test
	public void end() throws Exception {
		jobParameters.setProperty("exitStatus", "END");
		
		JobExecution jobExecution = startJob("on", jobParameters);
		
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
		assertThat(jobOperator.getStepExecutions(jobExecution.getExecutionId()).size(), is(1));
	}

	@Test
	public void stop() throws Exception {
		jobParameters.setProperty("exitStatus", "STOP");

		JobExecution jobExecution = startJob("on", jobParameters);
		
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.STOPPED));
		assertThat(jobOperator.getStepExecutions(jobExecution.getExecutionId()).size(), is(1));
	}
	
	@Test
	public void stopRestart() throws Exception {
		jobParameters.setProperty("exitStatus", "STOP");

		JobExecution jobExecution = startJob("on", jobParameters);
		
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.STOPPED));
		assertThat(jobOperator.getStepExecutions(jobExecution.getExecutionId()).size(), is(1));
		
		JobExecution newJobExecution = restartJob(jobExecution.getExecutionId(), jobParameters);
		
		assertThat(newJobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
		assertThat(jobOperator.getStepExecutions(newJobExecution.getExecutionId()).size(), is(1));
	}

}