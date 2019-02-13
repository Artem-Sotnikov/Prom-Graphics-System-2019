import java.awt.Color;
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
		this.setSize(800,800);
		
		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);
		
		this.requestFocusInWindow();
		this.setVisible(true);
	}
	
	public void displayFloorPlan() {
		this.disp.repaint();
	}
	
	public void exit() {
		this.dispose();
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
		public void mouseClicked(MouseEvent e) {
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
	
	private class Shape {
		private double x;
		private double y;
		
		public Shape(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * @return the x
		 */
		public double getX() {
			return x;
		}

		/**
		 * @param x the x to set
		 */
		public void setX(double x) {
			this.x = x;
		}

		/**
		 * @return the y
		 */
		public double getY() {
			return y;
		}

		/**
		 * @param y the y to set
		 */
		public void setY(double y) {
			this.y = y;
		}
	}
	
	private class Square extends Shape {
		private double width;
		private double height;
		
		public Square(double x, double y, double width, double height) {
			super(x, y);
			this.width = width;
			this.height = height;
		}
		
		public void draw(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillRect((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("System Operational");
	}
}
