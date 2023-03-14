package elevator;
public class Timer {

	private long total, start, stop;
	private boolean isRunning;
	
	public Timer() {
		reset();
	} //end Timer
	
	private void reset() {
		total = start = stop = 0;
		isRunning = false;
	} //end reset
	
	//starts the timer 
	public void start() {
		if(isRunning) {
			return;
		} else {
			isRunning = true;
			start = System.nanoTime();
		} //end if else 
	} //end start
	
	//stops the timer
	public void stop() {
		if(!isRunning) {
			return;
		} else {
			stop = System.nanoTime();
			isRunning = false;
			total += (stop - start);
		} //end if else 
	} //end stop 
	
	//return the total time from the stop watch 
	public long getTotal() {
		if(!isRunning) {
			return total;
		} //end if 
		return total + (System.nanoTime() - start);
	} //end getTotal
	
} //end class
