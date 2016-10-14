package mincan.q3;

import java.util.ArrayList;
import java.util.List;

public class Test3 {
	public final static int DEFAULT_NUM = 15;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int num = 0;
        if(args.length == 1 && args[0].matches("^[0-9]+")){
        	num = Integer.valueOf(args[1]);
        }else{
        	System.out.println("Usage: java Test3 <num>");
        	System.out.println("num must be integer");
        	System.out.println("Defualt running  PriorityCLH with 4 threads");
        	System.out.println("----------------------");
        }
        num = num==0? DEFAULT_NUM:num;
        System.out.println("You have create "+num+" threads");
        List<Thread> threads = new ArrayList<Thread>();
        Lock lock = new PriorityCLH();
        // create threads
        for(int i=0;i<num;i++) threads.add(new TestThread4(lock));
        // start threads
        for(int i=0;i<num;i++){
        	threads.get(i).start();
        }
        // wait for threads
        for(int i=0;i<num;i++){
        	threads.get(i).join();
        }
        System.out.println("\nTest 4 finished, final value " + TestThread4.num);
	}

}
