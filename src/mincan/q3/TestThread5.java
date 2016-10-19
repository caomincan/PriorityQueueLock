package mincan.q3;

import java.util.concurrent.atomic.AtomicInteger;

public class TestThread5 extends Thread {
public static AtomicInteger MAX_NUM_THREAD = new AtomicInteger(0);
	
	private Lock mylock;
	private int id;
	private long count;
	
	public TestThread5(Lock lock){
		mylock = lock;
		count = 0;
		id = MAX_NUM_THREAD.getAndIncrement();
	}
	
	@Override
	public void run(){
		System.out.println("Thread "+id+ " with priority " + mylock.getLabel()+" acquire Lock");
		if(mylock.tryLock(MAX_NUM_THREAD.get()*200)){
			System.out.println("Thread "+id+ " with priority " + mylock.getLabel()+" acquire success");
			mylock.unlock();
		}else{
			System.out.println("Thread "+id+ " with priority " + mylock.getLabel()+" acquire fail");
		}
	}
	
	public long getCount(){
		return count;
	}
}
