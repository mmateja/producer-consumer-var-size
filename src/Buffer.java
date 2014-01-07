import java.util.*;
import java.util.concurrent.locks.*;

public class Buffer {

	private Queue<Integer> buffer;
	private int bufferCapacity;

	private Lock lock = new ReentrantLock();

	private Condition firstProducerCondition = lock.newCondition();	
	private Condition firstConsumerCondition = lock.newCondition();
	
	private Condition otherProducersCondition = lock.newCondition();	
	private Condition otherConsumersCondition = lock.newCondition();

	private boolean firstProducerPositionOccupied = false;
	private boolean firstConsumerPositionOccupied = false;

	public Buffer(int capacity) {
		this.buffer = new ArrayDeque<>(capacity);
		this.bufferCapacity = capacity;
	}

	public void put(Integer[] values) {

		lock.lock();
		try {			

			// wait for first producer position to be unreserved
			while(firstProducerPositionOccupied) {
				try {
					otherProducersCondition.await();
				} catch (InterruptedException e) {
					// ignore
				}
			}

			// lock first position
			firstProducerPositionOccupied = true;

			// wait for available space in buffer
			while(freeSpace() < values.length) {
				try {
					firstProducerCondition.await();
				} catch (InterruptedException e) {
					// ignore
				}
			}

			// put elements to buffer
			for (Integer i : values) {
				buffer.add(i);
			}

//			// print info
//			System.out.println("Added " + values.length + " elements:");
//			printContent();

			// release first position and notify another threads
			firstProducerPositionOccupied = false;
			otherProducersCondition.signal();
			firstConsumerCondition.signal();
		} finally {			
			lock.unlock();
		}		

	}

	public Integer[] get(int numberOfElements) {

		Integer[] elements;

		lock.lock();		
		try {			

			// wait for first consumer position to be unreserved
			while(firstConsumerPositionOccupied) {
				try {
					otherConsumersCondition.await();
				} catch (InterruptedException e) {
					// ignore
				}
			}

			// lock first position
			firstConsumerPositionOccupied = true;

			// wait for available resources
			while(usedSpace() < numberOfElements) {
				try {
					firstConsumerCondition.await();
				} catch (InterruptedException e) {
					// ignore
				}
			}

			// get elements from buffer
			elements = new Integer[numberOfElements];
			for (int i=0; i<numberOfElements; i++) {
				elements[i] = buffer.remove();
			}

//			// print info
//			System.out.println("Removed " + numberOfElements + " elements:");
//			printContent();

			// release first position and notify another threads
			firstConsumerPositionOccupied = false;
			otherConsumersCondition.signal();
			firstProducerCondition.signal();
		} finally {
			lock.unlock();
		}
		return elements;
	}


	private int freeSpace() {
		return bufferCapacity - buffer.size();
	}

	private int usedSpace() {
		return buffer.size();
	}

//	private void printContent() {
//		for(Integer i : buffer) {
//			System.out.print(i + " ");
//		}
//		System.out.println();
//	}	

}

