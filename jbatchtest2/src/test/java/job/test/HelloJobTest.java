package job.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

import job.util.AbstractJobTest;

import org.junit.Test;

public class HelloJobTest extends AbstractJobTest {

	@Test
	public void test() throws Exception {
		Properties jobParameters = new Properties();
		JobExecution jobExecution = startJob("hello", jobParameters);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
	}
}
