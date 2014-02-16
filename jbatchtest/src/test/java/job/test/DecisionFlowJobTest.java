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
public class DecisionFlowJobTest extends AbstractJobTest {

	@Test
	public void test() throws Exception {
		Properties props = new Properties();
		JobExecution jobExecution = startJob("decision-flow", props);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
	}

}
