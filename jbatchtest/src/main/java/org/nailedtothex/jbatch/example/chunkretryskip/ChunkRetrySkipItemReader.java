package org.nailedtothex.jbatch.example.chunkretryskip;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nailedtothex.jbatch.example.chunk.ChunkInputItem;

@Named
public class ChunkRetrySkipItemReader implements ItemReader {
	private static final Logger log = Logger.getLogger(ChunkRetrySkipItemReader.class.getName());

	@Inject
	@BatchProperty
	private Integer failAt;
	@PersistenceContext
	private EntityManager em;

	private int index;
	private List<?> data;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		index = checkpoint == null ? 0 : (Integer) checkpoint;
		log.log(Level.FINE, "open(): checkpoint={0}, index starts at={1}", new Object[] { checkpoint, index });

		data = em.createQuery("SELECT c FROM ChunkInputItem c ORDER BY c.id", ChunkInputItem.class).getResultList();
	}

	@Override
	public Object readItem() throws Exception {
		log.log(Level.FINE, "readItem(): index={0}, failAt={1}", new Object[] { index, failAt });

		if (index == failAt) {
			throw new RuntimeException(String.valueOf(index++));
		}

		Object item = readItem0();
		log.log(Level.FINE, "readItem(): returning={0}", item);
		return item;
	}

	protected Object readItem0() throws Exception {
		if (index >= data.size()) {
			return null;
		}

		return data.get(index++);
	}

	@Override
	public void close() throws Exception {
		log.log(Level.FINE, "close()");
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		log.log(Level.FINE, "checkpointInfo(): returns={0}", index);
		return index;
	}
}