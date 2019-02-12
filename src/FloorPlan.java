import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
// import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FloorPlan extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Display disp;
	// private ArrayList<Table> tables;
	
	public FloorPlan() {
		super("Floor Plan");
		this.disp = new Display();
	}
	
	public static void main(String[] args) {
	}

	private class Display extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setDoubleBuffered(true);
			
			this.repaint();
		}
	}
	
	private class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
