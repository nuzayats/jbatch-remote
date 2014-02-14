package org.nailedtothex.jbatch.example.partition;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.partition.PartitionMapper;
import javax.batch.api.partition.PartitionPlan;
import javax.batch.api.partition.PartitionPlanImpl;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyPartitionMapper implements PartitionMapper {

	private static final Logger log = Logger.getLogger(MyPartitionMapper.class.getName());

	@Inject
	@BatchProperty
	private String partitions;

	@Override
	public PartitionPlan mapPartitions() throws Exception {
		log.log(Level.FINE, "mapPartitions(): partitions={0}", partitions);

		PartitionPlan partitionPlan = new PartitionPlanImpl();
		partitionPlan.setPartitions(Integer.parseInt(partitions));

		Properties[] partitionProperties = new Properties[partitionPlan.getPartitions()];
		for (int i = 0; i < partitionPlan.getPartitions(); i++) {
			Properties props = new Properties();
			props.setProperty("partitionNumber", String.valueOf(i));

			log.log(Level.FINE, "mapPartitions(): partitionProperty={0}", props);
			partitionProperties[i] = props;
		}
		partitionPlan.setPartitionProperties(partitionProperties);

		return partitionPlan;
	}

}
