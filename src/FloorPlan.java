import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FloorPlan extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Display disp;
	
	public FloorPlan() {
		super("Floor Plan");
	}
	
	private class Display extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics g) {
			
		}
	}
}
