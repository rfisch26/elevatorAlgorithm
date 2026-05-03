package elevator;

public class Timer {

    private long total, start, stop;
    private boolean isRunning;

    public Timer() {
        reset();
    }

    private void reset() {
        total = start = stop = 0;
        isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            start = System.nanoTime();
        }
    }

    public void stop() {
        if (isRunning) {
            stop = System.nanoTime();
            isRunning = false;
            total += (stop - start);
        }
    }

    public long getTotal() {
        if (isRunning) {
            return total + (System.nanoTime() - start);
        } else {
            return total;
        }
    }

}