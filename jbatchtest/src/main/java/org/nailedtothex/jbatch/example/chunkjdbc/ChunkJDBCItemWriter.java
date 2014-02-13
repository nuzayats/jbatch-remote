package org.nailedtothex.jbatch.example.chunkjdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.batch.api.chunk.ItemWriter;
import javax.inject.Named;
import javax.sql.DataSource;

import org.nailedtothex.jbatch.example.chunk.ChunkOutputItem;

@Named
public class ChunkJDBCItemWriter implements ItemWriter {
	private static final Logger log = Logger.getLogger(ChunkJDBCItemWriter.class.getName());

	@Resource(lookup = "java:comp/env/jdbc/jbatchtest")
	private DataSource dataSource;

	private int index;

	private Connection cn;
	private PreparedStatement ps;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		index = checkpoint == null ? 0 : (Integer) checkpoint;
		log.log(Level.FINE, "open(): checkpoint={0}, index starts at={1}", new Object[] { checkpoint, index });

		cn = dataSource.getConnection();
		ps = cn.prepareStatement("INSERT INTO chunkoutputitem (id, result) VALUES (?, ?)");
	}

	@Override
	public void close() throws Exception {
		log.log(Level.FINE, "close()");
		try {
			ps.close();
		} catch (SQLException e) {
		}
		try {
			cn.close();
		} catch (SQLException e) {
		}
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		log.log(Level.FINE, "checkpointInfo(): returns={0}", index);
		return index;
	}

	@Override
	public void writeItems(List<Object> items) throws Exception {
		log.log(Level.FINE, "writeItems(): index={0}", index);
		writeItems0(items);
	}

	protected void writeItems0(List<Object> items) throws Exception {
		for (Object item : items) {
			log.log(Level.FINE, "writeItems(): item={0}", item);
			ChunkOutputItem chunkOutputItem = (ChunkOutputItem) item;
			ps.setLong(1, chunkOutputItem.getId());
			ps.setInt(2, chunkOutputItem.getResult());
			ps.addBatch();
		}
		for (int i : ps.executeBatch()) {
			log.log(Level.FINE, "writeItems(): ps#executeBatch() result={0}", i);
		}
		index++;
	}
}