public class Consumer implements Runnable {

	private Buffer buffer;
	Integer[][] values;
	
	public Consumer(Buffer buffer, Integer[][] values) {
		this.buffer = buffer;
		this.values = values;
	}
	
	@Override
	public void run() {
		for(int i=0; i<values.length; i++) {
			buffer.get(values[i].length);
		}
	}

}
