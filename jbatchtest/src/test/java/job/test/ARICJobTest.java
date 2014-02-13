package job.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

import job.util.AbstractJobTest;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ARICJobTest extends AbstractJobTest{
	
	@Test
	public void test() throws Exception {
		Properties jobParameters = new Properties();
		
		
		// exception will thrown at step3
		jobParameters.setProperty("failAtStep3", "true");
		JobExecution jobExecution = startJob("aric", jobParameters);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.FAILED));
		
		// no exception will throw at step3
		jobParameters.setProperty("failAtStep3", "false");
		long executionId = jobExecution.getExecutionId();
		JobExecution jobExecutionAtRestart = restartJob(executionId, jobParameters);
		assertThat(jobExecutionAtRestart.getBatchStatus(), is(BatchStatus.COMPLETED));
	}
}
