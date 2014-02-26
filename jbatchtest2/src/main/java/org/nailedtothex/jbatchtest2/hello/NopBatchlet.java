package org.nailedtothex.jbatchtest2.hello;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named
public class NopBatchlet extends AbstractBatchlet {

	@Override
	public String process() throws Exception {
		System.out.println("hello");
		return null;
	}

}
