package org.nailedtothex.jbatch.example.partition;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.partition.PartitionReducer;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class MyPartitionReducer implements PartitionReducer {

	private static final Logger log = Logger.getLogger(MyPartitionReducer.class.getName());

	@PersistenceContext
	private EntityManager em;
	@Inject
	private StepContext stepContext;

	@Override
	public void beginPartitionedStep() throws Exception {
		log.fine("beginPartitionedStep()");

		// analyzer will add output data to this list  
		List<?> outputItems = new ArrayList<>();
		stepContext.setTransientUserData(outputItems);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void beforePartitionedStepCompletion() throws Exception {
		log.fine("beforePartitionedStepCompletion()");

		List<Object> outputItems = (List<Object>) stepContext.getTransientUserData();
		for(Object item : outputItems){
			log.log(Level.FINE, "beforePartitionedStepCompletion(): outputItem={0}", item);
			em.persist(item);
		}
	}

	@Override
	public void rollbackPartitionedStep() throws Exception {
		log.fine("rollbackPartitionedStep()");
	}

	@Override
	public void afterPartitionedStepCompletion(PartitionStatus status) throws Exception {
		log.log(Level.FINE, "afterPartitionedStepCompletion(): partitionStatus={0}", status);
	}
}
