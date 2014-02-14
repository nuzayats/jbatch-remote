package org.nailedtothex.jbatch.example.partition;

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
public class PartitionItemReader implements ItemReader {
	private static final Logger log = Logger.getLogger(PartitionItemReader.class.getName());

	@Inject
	@BatchProperty
	private String partitions;
	@Inject
	@BatchProperty
	private String partitionNumber;
	@Inject
	@BatchProperty
	private String failAt;
	@PersistenceContext
	private EntityManager em;

	private int index;
	private List<?> data;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		index = checkpoint == null ? 0 : (Integer) checkpoint;
		log.log(Level.FINE, "open(): checkpoint={0}, index starts at={1}, partitions={2}, partitionNumber={3}",
				new Object[] { checkpoint, index, partitions, partitionNumber });
		
		data = em.createQuery(
				"SELECT c FROM ChunkInputItem c WHERE MOD(c.id, :partitions) = :partitionNumber ORDER BY c.id", ChunkInputItem.class)
					.setParameter("partitions", Integer.valueOf(partitions))
					.setParameter("partitionNumber", Integer.valueOf(partitionNumber))
					.getResultList();
	}

	@Override
	public Object readItem() throws Exception {
		if(String.valueOf(index).equals(failAt)){
			log.log(Level.FINE, "readItem(): throw exception at index={0}", index);
			throw new RuntimeException(failAt);
		}
		
		Object item = readItem0();
		log.log(Level.FINE, "readItem(): index={0}, returning={1}", new Object[]{index, item});
		index++;
		return item;
	}
	
	protected Object readItem0() throws Exception{
		if (index >= data.size()) {
			return null;
		}

		return data.get(index);
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