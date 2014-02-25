package job.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

import job.util.AbstractJobTest;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JobWideProducerJobTest extends AbstractJobTest{
	
	@Test
	public void test() throws Exception {
		Properties jobParameters = new Properties();
		jobParameters.setProperty("baseDate", "20140225");
		JobExecution jobExecution = startJob("jobwideproducer", jobParameters);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
	}
}
