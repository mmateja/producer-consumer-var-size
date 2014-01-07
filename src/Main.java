import java.util.*;

public class Main {

	private static final int BUFFER_CAPACITY = 50;
	private static final int CONSUMER_THREADS = 1000;
	private static final int PRODUCER_THREADS = 1000;	

	public static void main(String[] args) {

		List<Thread> threads = new ArrayList<>(CONSUMER_THREADS+PRODUCER_THREADS);		
		Buffer buffer = new Buffer(BUFFER_CAPACITY);

		createConsumers(threads, buffer);

		createProducers(threads, buffer);

		startThreads(threads);

	}


	private static void startThreads(List<Thread> threads) {
		for(Thread t : threads) {
			t.start();
		}
	}


	private static void createProducers(List<Thread> threads, Buffer buffer) {
		for(int i=0; i<PRODUCER_THREADS; i++) {
			threads.add(new Thread(new Producer(buffer, BUFFER_CAPACITY/2)));
		}
	}


	private static void createConsumers(List<Thread> threads, Buffer buffer) {
		for(int i=0; i<CONSUMER_THREADS; i++) {
			threads.add(new Thread(new Consumer(buffer, BUFFER_CAPACITY/2)));
		}
	}

}
