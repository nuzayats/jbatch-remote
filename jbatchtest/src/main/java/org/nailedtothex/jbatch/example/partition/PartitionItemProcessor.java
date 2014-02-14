package org.nailedtothex.jbatch.example.partition;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;

import org.nailedtothex.jbatch.example.chunk.ChunkInputItem;
import org.nailedtothex.jbatch.example.chunk.ChunkOutputItem;

@Named
public class PartitionItemProcessor implements ItemProcessor {

	private static final Logger log = Logger.getLogger(PartitionItemProcessor.class.getName());

	@Inject
	@BatchProperty
	private String divide;
	@Inject
	@BatchProperty
	private String sleep;
	private int iDivide;
	private long lSleep;

	@PostConstruct
	void postConstruct() {
		log.log(Level.FINE, "postConstruct(): divide={0}, sleep={1}", new Object[] { divide, sleep });
		iDivide = Integer.parseInt(divide);
		lSleep = Long.parseLong(sleep);
	}

	@Override
	public Object processItem(Object item) throws Exception {
		ChunkInputItem chunkInputItem = (ChunkInputItem) item;

		ChunkOutputItem chunkOutputItem = new ChunkOutputItem();
		chunkOutputItem.setId(chunkInputItem.getId());
		chunkOutputItem.setResult(chunkInputItem.getInput() / iDivide);

		log.log(Level.FINE, "processItem(): input={0}, output={1}", new Object[] { chunkInputItem, chunkOutputItem });
		
		Thread.sleep(lSleep);

		return chunkOutputItem;
	}
}
