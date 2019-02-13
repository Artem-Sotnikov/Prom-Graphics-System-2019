import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

// import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FloorPlan extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Display disp;
	private ArrayList<Square> tableShapes;
	private ArrayList<Circle> studentShapes;
	
	// private ArrayList<Table> tables;
	
	public FloorPlan() {
		super("Floor Plan");
		this.disp = new Display();
		this.add(disp);
		
		this.setSize(1000,1000);
		
		
		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);
		
		this.requestFocusInWindow();
		this.setVisible(true);
		
		tableShapes = new ArrayList<Square>(0);
		studentShapes = new ArrayList<Circle>(0);
	}
	
	public void generateFloorPlan(ArrayList<Table> tables) {
		int tableSize = tables.get(0).getSize();
		double determinedX = 0;
		double determinedY = 0;
		
		for (int i = 0; i < tables.size(); i++) {
			Square tableCreation = new Square();
			tableCreation.setHeight(40);
			tableCreation.setWidth((tableSize/2)*10);	
			
			
			if (i == 0) {
				determinedX = 100;
				determinedY = 100;
				System.out.println("initial active");
			} else {
				determinedX = tableShapes.get(i - 1).getX() + 100;
				determinedY = tableShapes.get(i - 1).getY();
				if (determinedX > 900 - tableSize*10) {
					determinedX = 100;
					determinedY = determinedY + 100;
				}
				
																		
			}
			
			tableCreation.setX(determinedX);
			tableCreation.setY(determinedY);
			
			tableShapes.add(tableCreation);
			
		}
	}
	
	private class LoadFile {
		public ArrayList<Square> tableList;
		public ArrayList<Circle> studentList;
		
		LoadFile(ArrayList<Square> tableL,ArrayList<Circle> studentL) {
			this.studentList = studentL;
			this.tableList = tableL;
		}
		
		//temporary, do not use in final version
		LoadFile() {}
		
	}
	
	private LoadFile loadShapes() {		
		return new LoadFile();		
	}
	
	private void saveShapes() {
		// write to file this.shapes		
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
			
			for (int i = 0; i < studentShapes.size(); i++) {
				studentShapes.get(i).draw(g);				
			}
			
			for (int i = 0; i < tableShapes.size(); i++) {
				tableShapes.get(i).draw(g);
			}
			
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
	
	private abstract class Shape {
		private double x;
		private double y;
		
		public Shape(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public Shape() {};
		
		abstract void draw(Graphics g); 
		

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
		
		public Square() {};
		
		public double getWidth() {
			return width;
		}

		public void setWidth(double width) {
			this.width = width;
		}

		public double getHeight() {
			return height;
		}

		public void setHeight(double height) {
			this.height = height;
		}

		
		@Override
		public void draw(Graphics g) {
			Color IP_PURPLE = new Color(135,128,184);
			g.setColor(IP_PURPLE);
			g.fillRect((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
		}
	}
	
	private class Circle extends Shape {
		private double radius;
		
		public Circle(double x, double y, double rad) {
			super(x, y);
			this.radius = rad;
			// TODO Auto-generated constructor stub
		}
		
		public Circle() {};
		
		public double getRadius() {
			return this.radius;
		}
		
		public void setRadius(double rad) {
			this.radius = rad;
		}
		
		@Override
		public void draw(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillOval((int)this.getX(),(int)this.getY(),(int)this.radius,(int)this.radius);
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println("System Operational");
	}
}
