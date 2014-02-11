package org.nailedtothex.jbatchif;

import java.io.Serializable;

import javax.batch.runtime.Metric;

public class SimpleMetricImpl implements Metric, Serializable {

	private static final long serialVersionUID = 1l;

	private final MetricType type;
	private final long value;

	public SimpleMetricImpl(Metric metric) {
		this(metric.getType(), metric.getValue());
	}

	public SimpleMetricImpl(MetricType metricType, long value) {
		super();
		this.type = metricType;
		this.value = value;
	}

	@Override
	public MetricType getType() {
		return type;
	}

	@Override
	public long getValue() {
		return value;
	}

}
