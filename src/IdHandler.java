import java.util.Stack;

public class IdHandler {
	private int counter;
	Stack<Integer> available;
	public IdHandler() {
		counter = 0;
		available = new Stack<Integer>();
	}
	
	public void addAvailable(int id) {
		available.push(id);
	}
	
	public int getNewId() {
		if(available.isEmpty()) {
			return counter++;
		}else {
			return available.pop();
		}
	}
}
