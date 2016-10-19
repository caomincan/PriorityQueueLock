package mincan.q3;

import java.util.concurrent.atomic.AtomicInteger;

public class TestThread5 extends Thread {
public static AtomicInteger MAX_NUM_THREAD = new AtomicInteger(0);
	
	private Lock mylock;
	private int id;
	private long count;
	private long wait;
	
	public TestThread5(Lock lock,long time){
		mylock = lock;
		count = 0;
		id = MAX_NUM_THREAD.getAndIncrement();
		wait = time;
	}
	
	@Override
	public void run(){
		System.out.println("Thread "+id+ " with priority " + mylock.getLabel()+" acquire Lock");
		int try_num = 0;
		while(!mylock.tryLock(wait)){
			try_num++;
		}
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread "+id+ " with priority " + mylock.getLabel()+" acquire success with try time "+ try_num);
		mylock.unlock();
	}
	
	public long getCount(){
		return count;
	}
}
