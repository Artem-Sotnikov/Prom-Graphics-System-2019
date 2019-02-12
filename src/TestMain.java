import javax.swing.JFrame;

public class TestMain {	
	static JFrame window;
	
	public static void main(String[] args) {
		window = new FloorPlan();
		window.setVisible(true);
		window.setSize(800,800);
	}
}
