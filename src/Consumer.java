import java.util.Random;

public class Consumer implements Runnable {

	private Random random = new Random();
	private Buffer buffer;
	int maxNumberOfElements;
	
	public Consumer(Buffer buffer, int maxNumberOfElements) {
		this.buffer = buffer;
		this.maxNumberOfElements = maxNumberOfElements;
	}
	
	@Override
	public void run() {
		int numberOfElements;
		
		while(true) {
			numberOfElements = random.nextInt(maxNumberOfElements)+1;
			buffer.get(numberOfElements);
		}
	}

}
