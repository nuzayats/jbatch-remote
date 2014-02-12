package org.nailedtothex.jbatch.example.test;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class FugeUpdateBatchlet extends AbstractBatchlet{
 
	@PersistenceContext
	EntityManager em;
	
    @Override
    @Transactional
    public String process() throws Exception {
    	FugeEntity e = em.find(FugeEntity.class, 1l);
        e.setFuge("FugeModified");
        return "SUCCESS";
    }
}