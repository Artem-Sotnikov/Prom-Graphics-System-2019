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
		
		this.square = new Square(100, 100, 50, 50, Color.MAGENTA);
		this.info = new Info(150, 50, 80, 50, Color.GRAY, "Hello there.");
		
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
		private Color color;
		
		public Shape(double x, double y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
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
		
		/**
		 * @return the color
		 */
		public Color getColor() {
			return color;
		}
		
		public abstract void draw(Graphics g);
		
		public abstract Rectangle getBoundingBox();
	}
	
	private class Square extends Shape {
		private double width;
		private double height;
		
		public Square(double x, double y, double width, double height, Color color) {
			super(x, y, color);
			this.width = width;
			this.height = height;
		}
		
		@Override
		public void draw(Graphics g) {
			g.setColor(this.getColor());
			g.fillRect((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
		}

		@Override
		public Rectangle getBoundingBox() {
			return new Rectangle((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
		}

		/**
		 * @return the width
		 */
		public double getWidth() {
			return width;
		}

		/**
		 * @return the height
		 */
		public double getHeight() {
			return height;
		}
	}
	
	private class Info extends Square {
		private String text;
		
		public Info(double x, double y, double width, double height, Color color, String text) {
			super(x, y, width, height, color);
			this.text = text;
		}
		
		@Override
		public void draw(Graphics g) {
			g.setColor(this.getColor());
			g.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
			g.setColor(Color.WHITE);
			g.drawString(text, (int)this.getX(), (int)this.getY() + (int)this.getHeight()/2);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("System Operational");
	}
}
