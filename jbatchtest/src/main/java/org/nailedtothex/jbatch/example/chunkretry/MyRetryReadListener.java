package org.nailedtothex.jbatch.example.chunkretry;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.listener.RetryReadListener;
import javax.inject.Named;

@Named
public class MyRetryReadListener implements RetryReadListener {

	private static final Logger log = Logger.getLogger(MyRetryReadListener.class.getName());

	@Override
	public void onRetryReadException(Exception ex) throws Exception {
		log.log(Level.WARNING, "onRetryReadException()", ex);
	}

}
