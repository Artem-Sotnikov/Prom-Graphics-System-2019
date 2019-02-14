import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class FloorPlan extends JFrame {
 private static final long serialVersionUID = 1L;
 
 private Display disp;
 private ArrayList<DispRectangle> tableShapes;
 private ArrayList<DispStudent> studentShapes;
 private ArrayList<Shape> uiShapes;
 
 // private ArrayList<Table> tables;
// private Shape square;
// private Shape info;
 
 final Color IP_PURPLE = new Color(135,128,184);
 final Color LIGHT_GRAY = new Color(196,196,196);
 final int SCALE_FACTOR = 20;
 final int OFFSET_FACTOR = 5;
 
 final DispRectangle backButton = new DispRectangle(10,10,100,40);
		 
 public FloorPlan() {
  super("Floor Plan");
  this.disp = new Display();
  
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.add(this.disp);
  this.setSize(1000,1000);
  
//  this.square = new DispRectangle(100, 100, 50, 50);
//  this.info = new DispRectangle(150, 50, 80, 50);
  
  this.requestFocusInWindow();
  this.setVisible(true);
  
  tableShapes = new ArrayList<DispRectangle>(0);
  studentShapes = new ArrayList<DispStudent>(0);
  uiShapes = new ArrayList<Shape>(0);
    
 }
 
 public void generateFloorPlan(ArrayList<Table> tables) {
  int tableSize = tables.get(0).getSize();  
  double determinedX = 0;
  double determinedY = 0;
  
  for (int i = 0; i < tables.size(); i++) {
   DispRectangle tableCreation = new DispRectangle();
   tableCreation.setHeight(2*SCALE_FACTOR);
   tableCreation.setWidth(tableSize*SCALE_FACTOR/2); 
   
   
   if (i == 0) {
    determinedX = 100;
    determinedY = 100;
    //System.out.println("initial active");
   } else {
    determinedX = tableShapes.get(i - 1).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
    determinedY = tableShapes.get(i - 1).getY();
    if (determinedX > 900 - tableSize*SCALE_FACTOR/2) {
     determinedX = 100;
     determinedY = determinedY + SCALE_FACTOR*6;
    }
    
                  
   }
   
   tableCreation.setX(determinedX);
   tableCreation.setY(determinedY);
   
   tableShapes.add(tableCreation);
   
   for (int j = 0; j < tables.get(i).getStudents().size(); j++) {
	  
	   DispStudent studentCreation = new DispStudent();
	   
	   String temp = tables.get(i).getStudents().get(j).getName();
	   System.out.println(temp);
	   studentCreation.setRadius(SCALE_FACTOR - 2);
	   
	   if (j < (tableSize/2)) {		   
		   studentCreation.setX(determinedX + SCALE_FACTOR*j);
		   studentCreation.setY(determinedY - SCALE_FACTOR - OFFSET_FACTOR);
	   } else {
		   studentCreation.setX(determinedX + SCALE_FACTOR*(j - (tableSize/2)));
		   studentCreation.setY(determinedY + SCALE_FACTOR*2 + OFFSET_FACTOR);
	   }
	   
	   studentCreation.setOriginalStudent(tables.get(i).getStudents().get(j));
	   studentShapes.add(studentCreation);
	   
   }
   
  }
 }
 
 private class LoadFile {
  public ArrayList<DispRectangle> tableList;
  public ArrayList<DispCircle> studentList;
  
  LoadFile(ArrayList<DispRectangle> tableL,ArrayList<DispCircle> studentL) {
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
  
  private UIState state;
  
  private MyMouseListener mouseListener;
  
  public Display() {
   this.mouseListener = new MyMouseListener();
   this.addMouseListener(this.mouseListener);
   this.addMouseMotionListener(this.mouseListener);
   this.setBackground(LIGHT_GRAY); 
   this.state = UIState.STATE_VIEWING;
  }
  
  public void paintComponent(Graphics g) {
   super.paintComponent(g);
   setDoubleBuffered(true);
       
   
   for (int i = 0; i < studentShapes.size(); i++) {
    studentShapes.get(i).draw(g,Color.BLUE);    
   }
   
   for (int i = 0; i < tableShapes.size(); i++) {
    tableShapes.get(i).draw(g, IP_PURPLE);
   }
      
   for (int i = 0; i < uiShapes.size(); i++) {
	   uiShapes.get(i).draw(g, Color.YELLOW);
   }
	   
   if (this.state == UIState.STATE_STUDENT_SELECTED) {
	   g.setColor(Color.BLACK);
	   g.drawString("BACK",25,25);
   }
   
   Point mousePos = this.mouseListener.getPos();
//   if (square.getBoundingBox().contains(mousePos)) {
//    info.draw(g, Color.GREEN);
//   }
//   square.draw(g, Color.RED);
//   
   for (int i = 0; i < studentShapes.size(); i++) {
	   if (studentShapes.get(i).getBoundingBox().contains(mousePos)) {
		   
		   DispStudent currStudent = studentShapes.get(i);
		   
		   DispRectangle infoBox = new DispRectangle();
		   infoBox.setX(currStudent.getX() - SCALE_FACTOR*4 - 2);
		   infoBox.setY(currStudent.getY() - SCALE_FACTOR*2 - 2);
		   infoBox.setWidth(SCALE_FACTOR*4);
		   infoBox.setHeight(SCALE_FACTOR*2);
		   infoBox.draw(g,Color.MAGENTA);
		   
		   
		   g.fillOval((int)currStudent.getX(),(int)currStudent.getY(),(int)currStudent.getRadius(),(int)currStudent.getRadius());
		   
		   g.setColor(Color.BLUE); 
		   
		   
		   
		   
		   g.fillOval((int)currStudent.getX() + 3,(int)currStudent.getY() + 3,(int)currStudent.getRadius() - 6,(int)currStudent.getRadius() - 6);
		   
		   g.drawString(currStudent.getOriginalStudent().getName(),(int)infoBox.getX() + 5,(int)infoBox.getY() + 15);
		   
	   }
	   /*
	   g.setColor(Color.MAGENTA);
	   Rectangle current = studentShapes.get(i).getBoundingBox();
	   g.fillRect((int)current.getX(),(int)current.getY(),(int)current.getWidth(),(int)current.getHeight());
	   */
   }
      
   
   if (mouseListener.clickPending())  {
	   Point clickPos = mouseListener.getClick();
	   mouseListener.clickHandled();
	   
	   if (this.state == UIState.STATE_VIEWING) {
	   		   			  
		  for (int i = 0; i < studentShapes.size(); i++) {
			   if (studentShapes.get(i).getBoundingBox().contains(clickPos)) {
				   
				   this.state = UIState.STATE_STUDENT_SELECTED;
				   
				   System.out.println("yay!");
				   DispCircle highlight = new DispCircle();
				   
				   highlight.setX(studentShapes.get(i).getX());
				   highlight.setY(studentShapes.get(i).getY());
				   highlight.setRadius(studentShapes.get(i).getRadius());
				   highlight.setReferenceNumber(1);
				   
				   uiShapes.add(highlight);					   
				   uiShapes.add(backButton);
				   
				   
				   
				   
			   }
		   }
		  
	   } else if (this.state == UIState.STATE_STUDENT_SELECTED) {
		   	if (backButton.getBoundingBox().contains(clickPos)) {
		   		uiShapes.clear();
		   		this.state = UIState.STATE_VIEWING;
		   	}	   
	   }
	   
	   
	   
   }  
   
   this.repaint();
  }
 }

 private class MyMouseListener implements MouseInputListener, MouseMotionListener {
  private int x;
  private int y;
  private int clickX;
  private int clickY;
  private boolean clickHandled;

  public void clickHandled() {
	  this.clickHandled = true;
  }
  
  public boolean clickPending() {
	  return (!this.clickHandled);
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
	  clickX = e.getX();
	  clickY = e.getY();
	  this.clickHandled = false;
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
  }

  @Override
  public void mouseExited(MouseEvent arg0) {
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
  
  public Point getClick() {
	  return new Point(clickX,clickY);
  }
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

 
 public static void main(String[] args) {
  System.out.println("System Operational");
 }
}
