package io.micrometer.tracing.reporter.wavefront;

import java.util.concurrent.BlockingQueue;

/**
 * @author Moritz Halbritter
 */
public interface SpanMetrics {
	long reportDropped();
	void reportReceived();
	void reportErrors();
	void registerQueueSize(BlockingQueue<?> queue);
	void registerQueueRemainingCapacity(BlockingQueue<?> queue);
}
