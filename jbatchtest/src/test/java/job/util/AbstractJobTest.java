package job.util;

import java.util.Properties;

import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.JobExecution;

import job.test.TestJobTest;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractJobTest {
	private static final String JNDI_NAME = "java:global/jbatchtest/MyJobOperatorImpl!javax.batch.operations.JobOperator";
	private static final long POLLING_WAIT = 1000l;

	@Resource(lookup = JNDI_NAME)
	protected JobOperator jobOperator;
	
	@Deployment
	public static Archive<?> createDeploymentPackage() {
		Archive<?> archive = ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(TestJobTest.class.getPackage())
				.addPackage(AbstractJobTest.class.getPackage())
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return archive;
	}
	
	protected JobExecution startJob(String jobXMLName, Properties jobParameters) {
		long executionId = jobOperator.start(jobXMLName, jobParameters);
		return waitForFinish(executionId);
	}

	private JobExecution waitForFinish(long executionId) {
		JobExecution jobExecution = null;
		do {
			try {
				Thread.sleep(POLLING_WAIT);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			jobExecution = jobOperator.getJobExecution(executionId);
		} while (jobExecution.getEndTime() == null);
		return jobExecution;
	}
}