package org.nailedtothex.jbatch.example.partition;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.partition.PartitionAnalyzer;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyPartitionAnalyzer implements PartitionAnalyzer {

	private static final Logger log = Logger.getLogger(MyPartitionAnalyzer.class.getName());

	@Inject
	private StepContext stepContext;

	@Override
	@SuppressWarnings("unchecked")
	public void analyzeCollectorData(Serializable data) throws Exception {
		List<Object> dataList = (List<Object>) data;
		List<Object> outputItems = (List<Object>) stepContext.getTransientUserData();

		outputItems.addAll(dataList);

		log.log(Level.FINE, "analyzeCollectorData(): data={0}, receivedDataCount={1}", new Object[] { data, outputItems.size() });
	}

	@Override
	public void analyzeStatus(BatchStatus batchStatus, String exitStatus) throws Exception {
		log.log(Level.FINE, "analyzeStatus(): batchStatus={0}, exitStatus={1}",
				new Object[] { batchStatus, exitStatus });
	}
}
