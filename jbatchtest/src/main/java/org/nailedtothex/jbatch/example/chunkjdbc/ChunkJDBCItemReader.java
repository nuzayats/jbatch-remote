package org.nailedtothex.jbatch.example.chunkjdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.batch.api.chunk.ItemReader;
import javax.inject.Named;
import javax.sql.DataSource;

import org.nailedtothex.jbatch.example.chunk.ChunkInputItem;

@Named
public class ChunkJDBCItemReader implements ItemReader {
	private static final Logger log = Logger.getLogger(ChunkJDBCItemReader.class.getName());

	@Resource(lookup = "java:comp/env/jdbc/jbatchtest")
	private DataSource dataSource;

	private int index;

	private Connection cn;
	private PreparedStatement ps;
	private ResultSet rs;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		index = checkpoint == null ? 0 : (Integer) checkpoint;
		log.log(Level.FINE, "open(): checkpoint={0}, index starts at={1}", new Object[] { checkpoint, index });

		cn = dataSource.getConnection();
		ps = cn.prepareStatement("SELECT id, input, processed FROM chunkinputitem ORDER BY id OFFSET ?",
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
		ps.setFetchSize(3);
		ps.setInt(1, index);
		rs = ps.executeQuery();
	}

	@Override
	public Object readItem() throws Exception {
		log.log(Level.FINE, "readItem(): index={0}", index);
		Object item = readItem0();
		log.log(Level.FINE, "readItem(): returning={0}", item);
		return item;
	}

	protected Object readItem0() throws Exception {
		if (!rs.next()) {
			return null;
		}

		ChunkInputItem inputItem = new ChunkInputItem();

		inputItem.setId(rs.getLong("id"));
		inputItem.setInput(rs.getInt("input"));
		if (rs.wasNull()) {
			inputItem.setInput(null);
		}
		inputItem.setProcessed(rs.getBoolean("processed"));

		index++;

		return inputItem;
	}

	@Override
	public void close() throws Exception {
		log.log(Level.FINE, "close()");
		try {
			rs.close();
		} catch (SQLException e) {
		}
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
}