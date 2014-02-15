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
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ChunkRetrySkipJobTest extends AbstractJobTest {

	@Test
	@UsingDataSet({ "job/chunk/ChunkInputItem.yml", "job/chunk/ChunkOutputItem.yml" })
	@ShouldMatchDataSet(value = "job/chunkskip/expected.yml", orderBy = "id")
	public void test() throws Exception {
		Properties props = new Properties();
		props.setProperty("divide", "2");
		props.setProperty("itemReaderFailAt", "5");

		JobExecution jobExecution = startJob("chunkretryskip", props);
		assertThat(jobExecution.getBatchStatus(), is(BatchStatus.COMPLETED));
	}
}
