public class Producer implements Runnable {

	private Buffer buffer;
	Integer[][] values;
	
	public Producer(Buffer buffer, Integer[][] values) {
		this.buffer = buffer;
		this.values = values;
	}
	
	@Override
	public void run() {
		for(int i=values.length-1; i>=0; i--) {
			buffer.put(values[i]);
		}
	}

}
