import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {

	private Buffer buffer;
	int maxNumberOfElements;
	
	public Consumer(Buffer buffer, int maxNumberOfElements) {
		this.buffer = buffer;
		this.maxNumberOfElements = maxNumberOfElements;
	}
	
	@Override
	public void run() {
		int numberOfElements;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		while(true) {
			numberOfElements = random.nextInt(maxNumberOfElements)+1;
			buffer.get(numberOfElements);
		}
	}

}
