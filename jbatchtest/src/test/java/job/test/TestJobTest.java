package job.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;

import job.util.AbstractJobTest;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TestJobTest extends AbstractJobTest{
	
	@Test
	@UsingDataSet("common/fugeentity.yml")
	@ShouldMatchDataSet(value = "datasets/job/test/expected.yml", orderBy = "id")
	public void test() throws Exception {
		BatchStatus status = startJob("test", new Properties()).getBatchStatus();
		assertThat(status, is(BatchStatus.COMPLETED));
	}
}
