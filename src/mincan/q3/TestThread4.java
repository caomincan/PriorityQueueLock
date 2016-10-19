package mincan.q3;

import java.util.concurrent.atomic.AtomicInteger;

public class TestThread4 extends Thread{
	public static AtomicInteger MAX_NUM_THREAD = new AtomicInteger(0);
	public static int num = 0;
	
	private Lock mylock;
	private int id;
	private long count;
	
	public TestThread4(Lock lock){
		mylock = lock;
		count = 0;
		id = MAX_NUM_THREAD.getAndIncrement();
	}
	
	@Override
	public void run(){
		// warm up
		for(int i=0;i<100;i++){
			mylock.lock();
			mylock.unlock();
		}
		// Measurement 
		long start = System.nanoTime();
		long duration = 0;
		while( duration < 200000000){
			mylock.lock();
			count++;
			mylock.unlock();
			duration = System.nanoTime()-start;
			//System.out.println("Thread id "+id+" duration "+ duration);
		}
		// cool down
		for(int i=0;i<100;i++){
			mylock.lock();
			mylock.unlock();
		}
	}
	
	public long getCount(){
		return count;
	}
	
	public Lock getLock(){
		return mylock;
	}
}
