import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
//import java.util.ArrayList;

public class FloorPlan extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Display disp;
	// private ArrayList<Table> tables;
	private Shape square;
	private Shape info;
	
	public FloorPlan() {
		super("Floor Plan");
		this.disp = new Display();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(this.disp);
		this.setSize(1000,1000);
		
		this.square = new Square(100, 100, 50, 50);
		this.info = new Square(150, 50, 80, 50);
		
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
		
		private MyMouseListener mouseListener;
		
		public Display() {
			this.mouseListener = new MyMouseListener();
			this.addMouseListener(this.mouseListener);
			this.addMouseMotionListener(this.mouseListener);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setDoubleBuffered(true);
			
			Point mousePos = this.mouseListener.getPos();
			if (square.getBoundingBox().contains(mousePos)) {
				info.draw(g);
			}
			square.draw(g);
			this.repaint();
		}
	}

	private class MyMouseListener implements MouseInputListener, MouseMotionListener {
		private int x;
		private int y;

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}
		
		public Point getPos() {
			return new Point(x, y);
		}
	}
	
	private abstract class Shape {
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
		 * @return the y
		 */
		public double getY() {
			return y;
		}
		
		public abstract void draw(Graphics g);
		
		public abstract Rectangle getBoundingBox();
	}
	
	private class Square extends Shape {
		private double width;
		private double height;
		
		public Square(double x, double y, double width, double height) {
			super(x, y);
			this.width = width;
			this.height = height;
		}
		
		@Override
		public void draw(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillRect((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
		}

		@Override
		public Rectangle getBoundingBox() {
			return new Rectangle((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("System Operational");
	}
}
