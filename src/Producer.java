import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

	private Buffer buffer;
	int maxNumberOfElements;
	
	public Producer(Buffer buffer, int maxNumberOfElements) {
		this.buffer = buffer;
		this.maxNumberOfElements = maxNumberOfElements;
	}
	
	@Override
	public void run() {
		int numberOfElements;
		Integer[] elements;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		while(true) {
			numberOfElements = random.nextInt(maxNumberOfElements)+1;
			elements = new Integer[numberOfElements];
			for(int i=0; i<numberOfElements; i++) {
				elements[i] = random.nextInt(10);
			}
			buffer.put(elements);
		}
	}

}
