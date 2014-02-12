package org.nailedtothex.jbatch.example.chunk;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ChunkItemProcessor implements ItemProcessor {

	private static final Logger log = Logger.getLogger(ChunkItemProcessor.class.getName());

	@Inject
	@BatchProperty
    private String divide;
	private int iDivide;
	
	@PostConstruct
	void init(){
		log.log(Level.FINE, "chunkItemProcessor#postConstruct(): divide={0}", divide);
		iDivide = Integer.parseInt(divide);
	}
	
	@Override
	public Object processItem(Object item) throws Exception {
		ChunkInputItem chunkInputItem = (ChunkInputItem) item;

		ChunkOutputItem chunkOutputItem = new ChunkOutputItem();
		chunkOutputItem.setId(chunkInputItem.getId());
		chunkOutputItem.setResult(chunkInputItem.getInput() / iDivide);

		log.log(Level.FINE, "chunkItemProcessor#processItem(): input={0}, output={1}", new Object[]{chunkInputItem, chunkOutputItem});
		
		return chunkOutputItem;
	}
}
