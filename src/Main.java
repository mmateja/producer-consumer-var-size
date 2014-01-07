import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main implements Runnable{

	private static final int BUFFER_CAPACITY = 50;
	private static final int CONSUMER_THREADS = 1000;
	private static final int PRODUCER_THREADS = 1000;
	private static final int OPERATIONS_PER_THREAD = 100;
	
	private List<Thread> threads = new ArrayList<>(CONSUMER_THREADS+PRODUCER_THREADS);		
	private Buffer buffer = new Buffer(BUFFER_CAPACITY);
	private Integer[][] values;

	public static void main(String[] args) {
		System.out.println("Initializing...");
		Main app = new Main();
		Thread thread = new Thread(app);
		
		System.out.println("Performing test...");
		long time = System.currentTimeMillis();
		thread.start();
		try {			
			thread.join();			
		} catch (InterruptedException e) {
			// ignore
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Finished.");
		System.out.println("Execution time: " + time + "ms");
	}
	
	public Main() {
		initializeValuesArray();
		createConsumers();
		createProducers();
	}

	private void initializeValuesArray() {
		values = new Integer[OPERATIONS_PER_THREAD][];
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for(int i=0; i<OPERATIONS_PER_THREAD; i++) {
			int length = random.nextInt(BUFFER_CAPACITY/2)+1;
			values[i] = new Integer[length];
			for(int j=0; j<values[i].length; j++) {
				values[i][j] = random.nextInt(10);
			}
		}
	}

	private void startThreads() {
		for(Thread t : threads) {
			t.start();
		}
	}
	
	private void joinThreads() {
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}

	private void createProducers() {
		for(int i=0; i<PRODUCER_THREADS; i++) {
			threads.add(new Thread(new Producer(buffer, values)));
		}
	}

	private void createConsumers() {
		for(int i=0; i<CONSUMER_THREADS; i++) {
			threads.add(new Thread(new Consumer(buffer, values)));
		}
	}

	@Override
	public void run() {
		startThreads();
		joinThreads();
	}

}
