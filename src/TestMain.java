import javax.swing.JFrame;

public class TestMain {	
	static JFrame window;
	
	public static void main(String[] args) {
		window = new FloorPlan();
		while (true) {
			((FloorPlan) window).displayFloorPlan();
		}
	}
}
