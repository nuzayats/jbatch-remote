package org.nailedtothex.jbatch;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named
public class TestBatchlet extends AbstractBatchlet{
 
    @Override
    public String process() throws Exception {
        System.out.println("Hello JSR352");
        return "SUCCESS";
    }
}