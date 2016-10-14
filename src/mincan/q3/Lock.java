package mincan.q3;

public interface Lock {
	public void lock();
	public void unlock();
	public int getLabel();
}
