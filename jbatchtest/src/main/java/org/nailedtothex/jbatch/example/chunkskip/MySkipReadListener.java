package org.nailedtothex.jbatch.example.chunkskip;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.listener.SkipReadListener;
import javax.inject.Named;

@Named
public class MySkipReadListener implements SkipReadListener {

	private static final Logger log = Logger.getLogger(MySkipReadListener.class.getName());

	@Override
	public void onSkipReadItem(Exception ex) throws Exception {
		log.log(Level.WARNING, "onSkipReadItem()", ex);
	}

}
