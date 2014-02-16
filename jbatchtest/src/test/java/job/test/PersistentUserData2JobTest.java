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
public class PersistentUserData2JobTest extends AbstractJobTest {

	@Test
	public void test() throws Exception {
		Properties jobParameters = new Properties();

		JobExecution jobExecution = startJob("persistentuserdata2", jobParameters);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.FAILED));

		JobExecution newJobExecution = restartJob(jobExecution.getExecutionId(), jobParameters);
		assertThat(newJobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
	}
}
