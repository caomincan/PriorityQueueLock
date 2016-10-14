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
		int mynum = 0;
		for(int i=0;i<100;i++){	
			mylock.lock();
			mynum = num;
			num = mynum+1;
			System.out.println("Thread "+id+" priority "+mylock.getLabel()+ " counter:"+ num);
			mylock.unlock();
		}
	}
}
