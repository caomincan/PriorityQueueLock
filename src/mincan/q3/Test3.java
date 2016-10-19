package mincan.q3;

import java.util.ArrayList;
import java.util.List;


public class Test3 {
	public final static int DEFAULT_NUM = 4;
	public final static String PROOF = "Proof";
	public final static String MEASURE = "Measure";

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int num = 0;
		String method = "";
        if(args.length == 2 && args[1].matches("^[0-9]+") &&
        		(args[0].equals(PROOF)|| args[0].equals(MEASURE))){
        	num = Integer.valueOf(args[1]);
        	method = args[0];
        }else{
        	System.out.println("Usage: java mincan.q3.Test3 <Option> <Num>");
        	System.out.println("Option: Proof | Measure");
        	System.out.println("num must be integer");
        	System.out.println("Defualt Proof PriorityCLH with 4 threads");
        	System.out.println("----------------------");
        }
        num = num==0? DEFAULT_NUM:num;
        method = method.compareTo("") == 0? PROOF: method;
        System.out.println("You have create "+num+" threads");
        
        List<Thread> threads = new ArrayList<Thread>();
        //Lock lock = new PriorityCLH();
        Lock lock = new PriorityCLH();
        // start logic
        switch(method){
        case MEASURE:
        	 // create threads
            for(int i=0;i<num;i++) threads.add(new TestThread4(lock));
            Thread.sleep(1000);
            // start threads
            for(int i=0;i<num;i++) threads.get(i).start();
            // wait for threads
            int[] priority = new int[num];
            long[] count = new long[num];
            for(int i=0;i<num;i++){
            	TestThread4 th = (TestThread4) threads.get(i);
            	th.join();
            	count[i] = th.getCount();
            	priority[i] = th.getLock().getLabel();
            }
            // calculate average
            double sum_time = 0;
            for(int i=0;i<num;i++){
            	sum_time += 2000000.0/(double)(count[i]*priority[i]);
            }
            double ave_time = sum_time/num;
            System.out.println(num+" Threads with average waiting time :" + ave_time +" us");
        	break;
        case PROOF:
        	 // create threads
            for(int i=0;i<num;i++) threads.add(new TestThread5(lock));
            // start threads
            for(int i=0;i<num;i++) threads.get(i).start();
            // wait for threads
        	break;
        }
	}

}
