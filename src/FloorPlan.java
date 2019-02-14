import java.awt.Color;
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
 private ArrayList<GraphicTable> tableShapes;
 private ArrayList<GraphicStudent> studentShapes;
 
 // private ArrayList<Table> tables;
 private Shape square;
 private Shape info;
 
 final Color IP_PURPLE = new Color(135,128,184);
 final int SCALE_FACTOR = 20;
 final int OFFSET_FACTOR = 5;
 
 public FloorPlan() {
  super("Floor Plan");
  this.disp = new Display();
  
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.add(this.disp);
  this.setSize(1000,1000);
  
  this.square = new GraphicTable(100, 100, 50, 50);
  this.info = new GraphicTable(150, 50, 80, 50);
  
  this.requestFocusInWindow();
  this.setVisible(true);
  
  tableShapes = new ArrayList<GraphicTable>(0);
  studentShapes = new ArrayList<GraphicStudent>(0);
 }
 
 public void generateFloorPlan(ArrayList<Table> tables) {
  int tableSize = tables.get(0).getSize();  
  double determinedX = 0;
  double determinedY = 0;
  
  for (int i = 0; i < tables.size(); i++) {
   GraphicTable tableCreation = new GraphicTable();
   tableCreation.setHeight(2*SCALE_FACTOR);
   tableCreation.setWidth(tableSize*SCALE_FACTOR/2); 
   
   
   if (i == 0) {
    determinedX = 100;
    determinedY = 100;
    //System.out.println("initial active");
   } else {
    determinedX = tableShapes.get(i - 1).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
    determinedY = tableShapes.get(i - 1).getY();
    if (determinedX > 900 - tableSize*SCALE_FACTOR) {
     determinedX = 100;
     determinedY = determinedY + SCALE_FACTOR*6;
    }
    
                  
   }
   
   tableCreation.setX(determinedX);
   tableCreation.setY(determinedY);
   
   tableShapes.add(tableCreation);
   
   for (int j = 0; j < tables.get(i).getStudents().size(); j++) {
	  
	   GraphicStudent studentCreation = new GraphicStudent();
	   
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
  public ArrayList<GraphicTable> tableList;
  public ArrayList<GraphicStudent> studentList;
  
  LoadFile(ArrayList<GraphicTable> tableL,ArrayList<GraphicStudent> studentL) {
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
  
  private MyMouseListener mouseListener;
  
  public Display() {
   this.mouseListener = new MyMouseListener();
   this.addMouseListener(this.mouseListener);
   this.addMouseMotionListener(this.mouseListener);
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
   
   Point mousePos = this.mouseListener.getPos();
//   if (square.getBoundingBox().contains(mousePos)) {
//    info.draw(g, Color.GREEN);
//   }
//   square.draw(g, Color.RED);
//   
   for (int i = 0; i < studentShapes.size(); i++) {
	   if (studentShapes.get(i).getBoundingBox().contains(mousePos)) {
		   
		   GraphicStudent currStudent = studentShapes.get(i);
		   
		   GraphicTable infoBox = new GraphicTable();
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
 

 
 
 public static void main(String[] args) {
  System.out.println("System Operational");
 }
}
