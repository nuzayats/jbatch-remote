package job.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

import job.util.AbstractJobTest;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DecisionStepJobTest extends AbstractJobTest {

	private Properties jobParameters;
	
	@Before
	public void before(){
		jobParameters = new Properties();
		jobParameters.setProperty("divide", "2");
	}
	
	@Test
	@UsingDataSet({ "job/chunk/ChunkInputItem.yml", "job/chunk/ChunkOutputItem.yml" })
	@ShouldMatchDataSet(value = "job/chunk/expected.yml", orderBy = "id")
	public void stepNoSkip() throws Exception {
		jobParameters.setProperty("itemReaderFailAt", "-1");

		JobExecution jobExecution = startJob("decision-step", jobParameters);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
	}

	@Test
	@UsingDataSet({ "job/chunk/ChunkInputItem.yml", "job/chunk/ChunkOutputItem.yml" })
	@ShouldMatchDataSet(value = "job/chunkskip/expected.yml", orderBy = "id")
	public void stepSkip() throws Exception {
		jobParameters.setProperty("itemReaderFailAt", "5");

		JobExecution jobExecution = startJob("decision-step", jobParameters);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
	}

}
