package org.nailedtothex.jbatch.example.partition;

import java.io.Serializable;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.partition.PartitionCollector;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyPartitionCollector implements PartitionCollector {

	private static final Logger log = Logger.getLogger(MyPartitionCollector.class.getName());

	@Inject
	private StepContext stepContext;

	@Override
	public Serializable collectPartitionData() throws Exception {
		Serializable persistentUserData = stepContext.getPersistentUserData();
		log.log(Level.FINE, "collectPartitionData(): data={0}", new Object[] { persistentUserData });
		stepContext.setPersistentUserData((Serializable) Collections.EMPTY_LIST);
		return persistentUserData;
	}
}