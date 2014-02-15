package org.nailedtothex.jbatch.example.chunkretryskip;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;

import org.nailedtothex.jbatch.example.chunk.ChunkInputItem;
import org.nailedtothex.jbatch.example.chunk.ChunkOutputItem;

@Named
public class ChunkRetrySkipItemProcessor implements ItemProcessor {

	private static final Logger log = Logger.getLogger(ChunkRetrySkipItemProcessor.class.getName());

	@Inject
	@BatchProperty
	private Integer divide;

	@Override
	public Object processItem(Object item) throws Exception {
		ChunkInputItem chunkInputItem = (ChunkInputItem) item;

		ChunkOutputItem chunkOutputItem = new ChunkOutputItem();
		chunkOutputItem.setId(chunkInputItem.getId());
		chunkOutputItem.setResult(chunkInputItem.getInput() / divide);

		log.log(Level.FINE, "processItem(): input={0}, output={1}", new Object[] { chunkInputItem, chunkOutputItem });

		return chunkOutputItem;
	}
}
