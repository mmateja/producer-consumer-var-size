import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {

	private Buffer buffer;
	private int maxNumberOfElementsToGet;
	private int totalNumberOfElementsLeft;
	
	public Consumer(Buffer buffer, int maxNumberOfElementsToGet, int totalNumberOfElements) {
		this.buffer = buffer;
		this.maxNumberOfElementsToGet = maxNumberOfElementsToGet;
		this.totalNumberOfElementsLeft = totalNumberOfElements;
	}
	
	@Override
	public void run() {
		int numberOfElements;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		while(totalNumberOfElementsLeft > 0) {
			numberOfElements = totalNumberOfElementsLeft <= maxNumberOfElementsToGet ? totalNumberOfElementsLeft : random.nextInt(maxNumberOfElementsToGet)+1;
			totalNumberOfElementsLeft -= numberOfElements;
			
			buffer.get(numberOfElements);
		}
	}

}
