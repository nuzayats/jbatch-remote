package org.nailedtothex.jbatch.example.chunk;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.ItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class ChunkItemWriter implements ItemWriter {
	private static final Logger log = Logger.getLogger(ChunkItemWriter.class.getName());

	@PersistenceContext
	private EntityManager em;

	private int index;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		index = checkpoint == null ? 0 : (Integer) checkpoint;
		log.log(Level.FINE, "chunkItemWriter#open(): checkpoint={0}, index starts at={1}", new Object[] { checkpoint, index });
	}


	@Override
	public void close() throws Exception {
		log.log(Level.FINE, "chunkItemWriter#close()");
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		log.log(Level.FINE, "chunkItemWriter#checkpointInfo(): returns={0}", index);
		return index;
	}

	@Override
	public void writeItems(List<Object> items) throws Exception {
		log.log(Level.FINE, "chunkItemWriter#writeItems(): index={0}", index);
		writeItems0(items);
	}
	
	protected void writeItems0(List<Object> items) throws Exception{
		for(Object item : items){
			log.log(Level.FINE, "chunkItemWriter#writeItems(): item={0}", item);
			em.persist(item);
		}
		index++;
	}
}