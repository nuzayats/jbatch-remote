package job.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.batch.runtime.BatchStatus;

import job.util.AbstractJobTest;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PersistentUserData1JobTest extends AbstractJobTest{
	
	@Test
	public void test() throws Exception {
		BatchStatus status = startJob("persistentuserdata1", new Properties()).getBatchStatus();
		assertThat(status, is(BatchStatus.COMPLETED));
	}
}
