/*
 * CLHLock.java
 *
 * Created on January 20, 2006, 11:35 PM
 *
 * From "Multiprocessor Synchronization and Concurrent Data Structures",
 * by Maurice Herlihy and Nir Shavit.
 * Copyright 2006 Elsevier Inc. All rights reserved.
 */

package mincan.q3; 

import java.lang.ThreadLocal;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.*;


public class PriorityCLH implements Lock {
  // use to random setting priority
  private static final Random rand = new Random();
  // priority queue capacity 
  private static final int MAX_NUM_THREAD = 100;
  // priority queue user defined comparator
  private static final Comparator<QNode> comp =  new Comparator<QNode>(){
	  public int compare(QNode a, QNode b){
		  return a.label-b.label; 
	  }
  };
  // priority queue
  private static PriorityBlockingQueue<QNode> queue = new PriorityBlockingQueue<QNode>(MAX_NUM_THREAD,comp);
  private AtomicReference<QNode> curr;
  // thread-local variables
  private ThreadLocal<QNode> myNode;
  
  /**
   * Constructor
   */
  public PriorityCLH() {
	curr = new AtomicReference<QNode>(null);
    // initialize thread-local variables
    myNode = new ThreadLocal<QNode>() {
      protected QNode initialValue() {
        return new QNode();
      }
    };
  }
  
  public void lock() {
    QNode qnode = myNode.get(); // use my node
    qnode.locked = true;
    QNode pred = curr.getAndSet(qnode);
    queue.offer(qnode); 
    if(pred == null) qnode.locked = false;
    while (qnode.locked) {} 
    
  }
  public void unlock() {
    QNode qnode = myNode.get(); // use my node
    queue.remove(qnode);
    if(queue.size() == 0){
    	if(curr.compareAndSet(qnode, null))
    		return;
    	while(queue.peek() == null){}
    }
    queue.peek().locked=false;
  }
 
  static class QNode {  // Queue node inner class
	public volatile int label = 1;
    public volatile boolean locked = false;
    
    public QNode(){
    	label = rand.nextInt(5)+1;
    };
    
    public String toString(){
    	return String.valueOf(locked);
    }
  }
  
  public String toString(){
	  Iterator<QNode> it = queue.iterator();
	  String result = "";
	  while(it.hasNext()){
		  QNode tmp = it.next();
		  result += tmp.toString()+" ";
	  }
	  return result;
  }
@Override
public int getLabel() {
	// TODO Auto-generated method stub
	return myNode.get().label;
}

@Override
public boolean tryLock(long time) {
	// TODO Auto-generated method stub
   QNode qnode = myNode.get(); // use my node
   qnode.locked = true;
   QNode pred = curr.getAndSet(qnode);
   queue.offer(qnode);
   // first node
   if(pred == null){
	   qnode.locked = false;
	   return true;
   }
   long start = System.nanoTime();
   long duration = 0;
   while (duration < time*2000 ) {
	   if(!qnode.locked) return true;
	   duration = System.nanoTime()-start;
   } 
   queue.remove(qnode);
   return false;
}
}


