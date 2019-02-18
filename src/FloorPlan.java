import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JPanel;

//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import javax.swing.event.MouseInputListener;

import static constant.Constants.*;

public class FloorPlan extends JFrame {
 private static final long serialVersionUID = 1L;

 private Display disp;
 private ArrayList<DispRectangle> tableShapes;
 private ArrayList<DispStudent> studentShapes;
 private ArrayList<Shape> uiShapes;
 private ArrayList<Shape> switchUIShapes;
 
 private int MAX_TOP = 0;
 private int MAX_LEFT = 0;
 private int MAX_BOTTOM = 2000;
 private int MAX_RIGHT = 2000;

 final DispRectangle backButton = new DispRectangle(10,10,100,40);
 final DispRectangle saveButton = new DispRectangle(SCALE_FACTOR*10,10,100,40);
 final DispRectangle loadButton = new DispRectangle(SCALE_FACTOR*10 + 110,10,100,40);
 final DispRectangle switchButton = new DispRectangle(10,60,100,40);

 private DispRectangle tableHighlight;
 private DispCircle selectedStudent;

 private LoadFile loadFile = new LoadFile();

 public FloorPlan() {
  super("Floor Plan");
  this.disp = new Display();
  JPanel sideBar = new JPanel();
  
  Dimension sideBarSize = new Dimension(200,(int) SCREEN_SIZE.getHeight());
  
  sideBar.setPreferredSize(sideBarSize);
  sideBar.setBackground(Color.ORANGE); 
  
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.add(this.disp, BorderLayout.CENTER);
  this.add(sideBar,BorderLayout.WEST);
  this.setSize(1000,1000);

  this.requestFocusInWindow();
  //this.setVisible(true);

  this.tableShapes = new ArrayList<DispRectangle>(0);
  this.studentShapes = new ArrayList<DispStudent>(0);
  this.uiShapes = new ArrayList<Shape>(0);
  this.switchUIShapes = new ArrayList<Shape>(0);

  selectedStudent = new DispCircle();
  tableHighlight = new DispRectangle();

  saveButton.setPrivateColor(Color.CYAN);
  loadButton.setPrivateColor(Color.CYAN);

  uiShapes.add(saveButton);
  uiShapes.add(loadButton);
 }

 public void loadShapesFromFile() {
  loadFile.loadShapes();
  tableShapes = loadFile.getTableList();
  studentShapes = loadFile.getStudentList();
 }

 public void saveShapesToFile() {
  loadFile.setTableList(this.tableShapes);
  loadFile.setStudentList(this.studentShapes);
  loadFile.saveShapes();
 }

 public void generateFloorPlan(ArrayList<Table> tables) {
  
	 int tableSize = tables.get(0).getSize(); 

	 this.MAX_RIGHT = (int) ((Math.ceil(Math.sqrt(tables.size())))*((tableSize/2) + 2)*SCALE_FACTOR + 200);
	 System.out.println(MAX_RIGHT);
	 this.MAX_BOTTOM = this.MAX_RIGHT;
	 
  
  double determinedX = 0;
  double determinedY = 0;

  for (int i = 0; i < tables.size(); i++) {
   DispTable tableCreation = new DispTable();
   
   tableCreation.setReal(true);
   
   tableCreation.setHeight(2*SCALE_FACTOR);
   tableCreation.setWidth(tableSize*SCALE_FACTOR/2); 

   if (i == 0) {
    determinedX = SCALE_FACTOR*10;
    determinedY = 100;
   } else {
    determinedX = tableShapes.get(i - 1).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
    determinedY = tableShapes.get(i - 1).getY();
    System.out.println(determinedX);
    if (determinedX > (this.MAX_RIGHT - SCALE_FACTOR*10 - tableSize*SCALE_FACTOR/2)) {
     determinedX = SCALE_FACTOR*10;
     determinedY = determinedY + SCALE_FACTOR*6;
    }
   }

   tableCreation.setX(determinedX);
   tableCreation.setY(determinedY);

   tableShapes.add(tableCreation);

   for (int j = 0; j < tables.get(i).getStudents().size(); j++) {
    DispStudent studentCreation = new DispStudent();
    
    studentCreation.setReal(true);

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
  
  boolean boardExhausted = false;
  
  while (!boardExhausted) {	  
	  
	  DispTable tableCreation = new DispTable();
	   
	   tableCreation.setReal(false);
	   
	   tableCreation.setHeight(2*SCALE_FACTOR);
	   tableCreation.setWidth(tableSize*SCALE_FACTOR/2);   
	  

      determinedX = tableShapes.get(tableShapes.size() - 1).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
      determinedY = tableShapes.get(tableShapes.size() - 1).getY();
      if (determinedX > MAX_RIGHT - 100 - tableSize*SCALE_FACTOR/2) {
       determinedX = SCALE_FACTOR*10;
       determinedY = determinedY + SCALE_FACTOR*6;
      }
      
  	
  	tableCreation.setX(determinedX);
  	tableCreation.setY(determinedY);
  	
  	System.out.println(determinedY);
  	
  	if (determinedY > (MAX_BOTTOM - SCALE_FACTOR*10 - 200)) {
  		boardExhausted = true;
  	} else {
  		tableShapes.add(tableCreation);
  	}
  	
  	
  }
  
 }

 public void displayFloorPlan() {

  this.setVisible(true);
  
  while (true) {  
   this.disp.repaint();
  }  
 }

 public void exit() {
  this.dispose();
 }

 private class Display extends JPanel {
  private static final long serialVersionUID = 1L;

  private UIState state;
  private MyMouseListener mouseListener;
  private int camX = 0;
  private int camY = 0;
  private int dx = 0;
  private int dy = 0;

  public Display() {
   this.mouseListener = new MyMouseListener();
   this.addMouseListener(this.mouseListener);
   this.addMouseMotionListener(this.mouseListener);
   this.addMouseWheelListener(this.mouseListener);
   this.setBackground(LIGHT_GRAY); 

   this.state = UIState.STATE_VIEWING;

   switchButton.setPrivateColor(Color.ORANGE);
   backButton.setPrivateColor(Color.YELLOW);

  }

  public void paintComponent(Graphics g) {
   super.paintComponent(g);
   setDoubleBuffered(true);

   updateCamera(g);

   for (int i = 0; i < studentShapes.size(); i++) {
    studentShapes.get(i).drawObject(g);    
   }

   for (int i = 0; i < tableShapes.size(); i++) {
    ((DispTable) tableShapes.get(i)).drawObject(g);
   }

   for (int i = 0; i < switchUIShapes.size(); i++) {
    switchUIShapes.get(i).draw(g);      
   }

   for (int i = 0; i < uiShapes.size(); i++) {
    uiShapes.get(i).draw(g);      
   }

   // text for buttons
   g.setColor(Color.BLACK);
   g.drawString("SAVE", (int)(saveButton.getX() + saveButton.getWidth()/2 - 25), (int)(saveButton.getY() + saveButton.getHeight()/2));
   g.drawString("LOAD", (int)(loadButton.getX() + loadButton.getWidth()/2 - 25), (int)(loadButton.getY() + loadButton.getHeight()/2));

   if (this.state != UIState.STATE_VIEWING) {
    g.setColor(Color.BLACK);
    g.drawString("BACK",25,25);

    if (this.state == UIState.STATE_STUDENT_SELECTED || this.state == UIState.STATE_TABLE_SELECTED) {
     g.drawString("SWITCH WITH",25,25 + 50);
    }
   }

   Point mousePos = this.mouseListener.getPos();
   mousePos.x = (int) (mousePos.x * mouseListener.getZoomScale() + camX);
   mousePos.y = (int) (mousePos.y * mouseListener.getZoomScale() + camY);

   
   // Student hover is set and text box displayed directly
   if (mouseListener.isDragging() == false) {
    for (int i = 0; i < studentShapes.size(); i++) {
     if (studentShapes.get(i).getBoundingBox().contains(mousePos)) {
    	 
      studentShapes.get(i).setHovered(true);
      studentShapes.get(i).drawBox(g);      
      
     } else {
    	 studentShapes.get(i).setHovered(false); 
     }
    }
   }
   
// Table hover is set and text box displayed directly
    if (mouseListener.isDragging() == false) {
     for (int i = 0; i < tableShapes.size(); i++) {
      if (tableShapes.get(i).getBoundingBox().contains(mousePos)) {
     	 
       tableShapes.get(i).setHovered(true);
       ((DispTable) tableShapes.get(i)).drawBox(g,i);      
       
      } else {
     	 tableShapes.get(i).setHovered(false); 
      }
    }
    
    

    if (mouseListener.clickPending())  {
     Point clickPos = mouseListener.getClick();
     clickPos.x += camX;
     clickPos.y += camY;
     mouseListener.clickHandled();

     // click save or load button
     if (saveButton.getBoundingBox().contains(clickPos)) {
      saveShapesToFile();
     } else if (loadButton.getBoundingBox().contains(clickPos)) {
      loadShapesFromFile();
     }

     if (this.state == UIState.STATE_VIEWING) {
      for (int i = 0; i < studentShapes.size(); i++) {
       if (studentShapes.get(i).getBoundingBox().contains(clickPos)) {

        this.state = UIState.STATE_STUDENT_SELECTED;

        studentShapes.get(i).setSelected(true);
        selectedStudent = studentShapes.get(i);
              
        uiShapes.add(backButton);
        uiShapes.add(switchButton);
       }
      }

      for (int i = 0; i < tableShapes.size(); i++) {
       if (tableShapes.get(i).getBoundingBox().contains(clickPos)) {

        this.state = UIState.STATE_TABLE_SELECTED;

        tableShapes.get(i).setSelected(true);        
        
        uiShapes.add(backButton);
        uiShapes.add(switchButton);
       }
      }
     
     } else if (this.state == UIState.STATE_STUDENT_SELECTED || this.state == UIState.STATE_TABLE_SELECTED) {

      if (backButton.getBoundingBox().contains(clickPos)) {
       uiShapes.clear();
       uiShapes.add(saveButton);
       uiShapes.add(loadButton);
       
       this.state = UIState.STATE_VIEWING;
       
       for (int i = 0; i < studentShapes.size(); i++) {
    	   studentShapes.get(i).setSelected(false);
       }
       
       for (int i = 0; i < tableShapes.size(); i ++) {
    	   tableShapes.get(i).setSelected(false);
       }
       
      } else if (switchButton.getBoundingBox().contains(clickPos)) {

       uiShapes.remove(switchButton);

       switch (this.state) {      
       case STATE_STUDENT_SELECTED:

        this.state = UIState.STATE_STUDENT_MOVING;
        
        DispRectangle tableSearch = new DispRectangle(selectedStudent.getX() - 10,selectedStudent.getY() - 10,
        		selectedStudent.getRadius() + 20, selectedStudent.getRadius() + 20);
        
        
        for (int i = 0; i < tableShapes.size(); i++) {
        	DispRectangle currTable = tableShapes.get(i); 
        	
        	if (tableSearch.getBoundingBox().intersects(currTable.getBoundingBox())) {
        		i = tableShapes.size();
        		
        		DispRectangle studentSearch = new DispRectangle(currTable.getX() - 10,currTable.getY() - 10,
        				currTable.getWidth() + 20,currTable.getHeight() + 20);
        		
        		for (int j = 0; j < studentShapes.size(); j++) {
        			if (studentSearch.getBoundingBox().intersects(studentShapes.get(j).getBoundingBox())) {
        				
        				studentShapes.get(j).setHighlighted(true);
        				
        			}        			
        		}        	
        	}
        }
        
        
        
        break;
       case STATE_TABLE_SELECTED:
    	   
        this.state = UIState.STATE_TABLE_MOVING;
        
        for (int i = 0; i < tableShapes.size(); i++) {
        	tableShapes.get(i).setHighlighted(true);
        }
        break;

       default:
        break;
       }                 
      }           
     } else if (this.state == UIState.STATE_STUDENT_MOVING) {

      if (backButton.getBoundingBox().contains(clickPos)) {
       uiShapes.add(switchButton);
       this.state = UIState.STATE_STUDENT_SELECTED;
       switchUIShapes.clear();
       
       for (int i = 0; i < studentShapes.size(); i++) {
    	studentShapes.get(i).setHighlighted(false);   
       }
       
      }

     } else if (this.state ==  UIState.STATE_TABLE_MOVING) {
      if (backButton.getBoundingBox().contains(clickPos)) {
       uiShapes.add(switchButton);
       this.state = UIState.STATE_TABLE_SELECTED;
       switchUIShapes.clear();
       
       for (int i = 0; i < tableShapes.size(); i++) {
       	tableShapes.get(i).setHighlighted(false);   
       }
       
      }
     }    
    }
   } 
  }

  public void updateCamera(Graphics g) {
   zooming(g);
   panning(g);
  }

  public void zooming(Graphics g) {
   Graphics2D g2 = (Graphics2D) g;
   Dimension d = this.getSize();
   g2.translate(d.width/2, d.height/2);
   g2.scale(mouseListener.getZoomScale(), mouseListener.getZoomScale());
   g2.translate(-d.width/2, -d.height/2);
  }

  public void panning(Graphics g) {
   if (mouseListener.isDragging()) {
    dx = (int) (mouseListener.getReleaseX() - mouseListener.getClick().getX());
    dy = (int) (mouseListener.getReleaseY() - mouseListener.getClick().getY());
    int totalX = camX + dx;
    int totalY = camY + dy;
    if (totalX < MAX_LEFT) {
     totalX = MAX_LEFT;
    } else if (totalX > MAX_RIGHT) {
     totalX = MAX_RIGHT;
    }
    if (totalY < MAX_TOP) {
     totalY = MAX_TOP;
    } else if (totalY > MAX_BOTTOM) {
     totalY = MAX_BOTTOM;
    }
    g.translate(-totalX, -totalY);
   } else {
    camX += dx;
    camY += dy;
    if (camX < MAX_LEFT) {
     camX = MAX_LEFT;
    } else if (camX > MAX_RIGHT) {
     camX = MAX_RIGHT;
    }
    if (camY < MAX_TOP) {
     camY = MAX_TOP;
    } else if (camY > MAX_BOTTOM) {
     camY = MAX_BOTTOM;
    }
    dx = 0;
    dy = 0;
    g.translate(-camX, -camY);
   }
  }
 }
}
