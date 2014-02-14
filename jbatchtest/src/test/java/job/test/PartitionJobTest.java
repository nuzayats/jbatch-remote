package job.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;

import job.util.AbstractJobTest;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PartitionJobTest extends AbstractJobTest {

	Properties jobParameters;

	@Before
	public void init() {
		jobParameters = new Properties();
		jobParameters.setProperty("divide", "2");
		jobParameters.setProperty("sleep", "1000");
	}

	@Test
	@UsingDataSet({ "job/chunk/ChunkInputItem.yml", "job/chunk/ChunkOutputItem.yml" })
	@ShouldMatchDataSet(value = "job/chunk/expected.yml", orderBy = "id")
	public void onePartition() throws Exception {
		jobParameters.setProperty("partitions", "1");

		BatchStatus status = startJob("partition", jobParameters).getBatchStatus();
		assertThat(status, is(BatchStatus.COMPLETED));
	}

	@Test
	@UsingDataSet({ "job/chunk/ChunkInputItem.yml", "job/chunk/ChunkOutputItem.yml" })
	@ShouldMatchDataSet(value = "job/chunk/expected.yml", orderBy = "id")
	public void twoPartition() throws Exception {
		jobParameters.setProperty("partitions", "2");

		BatchStatus status = startJob("partition", jobParameters).getBatchStatus();
		assertThat(status, is(BatchStatus.COMPLETED));
	}
}
