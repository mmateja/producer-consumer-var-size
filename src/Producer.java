import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

	private Buffer buffer;
	private int maxNumberOfElementsToPut;
	private int totalNumberOfElementsLeft;
	
	public Producer(Buffer buffer, int maxNumberOfElementsToPut, int totalNumberOfElements) {
		this.buffer = buffer;
		this.maxNumberOfElementsToPut = maxNumberOfElementsToPut;
		this.totalNumberOfElementsLeft = totalNumberOfElements;
	}
	
	@Override
	public void run() {
		int numberOfElements;
		Integer[] elements;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		while(totalNumberOfElementsLeft > 0) {
			numberOfElements = totalNumberOfElementsLeft <= maxNumberOfElementsToPut ? totalNumberOfElementsLeft : random.nextInt(maxNumberOfElementsToPut)+1;
			totalNumberOfElementsLeft -= numberOfElements;
			
			elements = new Integer[numberOfElements];
			for(int i=0; i<numberOfElements; i++) {
				elements[i] = random.nextInt(10);
			}
			buffer.put(elements);			
		}
	}

}
