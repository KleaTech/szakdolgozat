package hu.kleatech.jigsaw.scripting.inner;

import java.util.concurrent.*;

public class PollingFuture<T> implements Future<T> {
    public PollingFuture() {
        this(1);
    }
    public PollingFuture(int pollInterval) {
        this.pollInterval = pollInterval;
    }
    
    private final int pollInterval;
    private volatile T result;
    
    synchronized void set(T result) {
        if (this.result != null) throw new IllegalStateException("Result can only be set once");
        this.result = result;
    }
    
    @Override public boolean cancel(boolean bln) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public boolean isCancelled() { return false; }
    @Override public boolean isDone() { return result != null; }
    @Override public T get() throws InterruptedException, ExecutionException {
        while (result == null) Thread.sleep(pollInterval);
        return result;
    }
    @Override public T get(long l, TimeUnit tu) throws InterruptedException, ExecutionException, TimeoutException {
        long timespan = tu.toMillis(l);
        while (result == null) {
            Thread.sleep(pollInterval);
            timespan--;
            if (timespan <= 0) throw new TimeoutException();
        }
        return result;
    }
}
