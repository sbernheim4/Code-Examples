package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Filter;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Queue;

public abstract class ConcurrentFilter extends Filter implements Runnable {

	// Changed Queue to be a LinkedBlockingQueue
	protected Queue<String> input;
	protected Queue<String> output;

	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}

	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter sequentialNext = (ConcurrentFilter) nextFilter;
			this.next = sequentialNext;
			sequentialNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			sequentialNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}

	public void process(){
		while (!input.isEmpty() && !isDone()) {
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}
		// Random UUID4 that is cryptographically never going to occur again. Used to determine if there are any filters
		// left in the queue for isDone.
		output.add("170ee8d8-6783-45f9-b0c8-aed771c98e93");
	}

	@Override
	public boolean isDone() {
		try {
			return input.peek().equals("170ee8d8-6783-45f9-b0c8-aed771c98e93");
		} catch (NullPointerException e) {
			return true;
		}
	}

	protected abstract String processLine(String line);


}
