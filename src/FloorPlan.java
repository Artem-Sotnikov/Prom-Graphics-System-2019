import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
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
	private ArrayList<Shape> switchUIShapes;

	final Color IP_PURPLE = new Color(135,128,184);
	final Color LIGHT_GRAY = new Color(196,196,196);
	final int SCALE_FACTOR = 20;
	final int OFFSET_FACTOR = 5;
	Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();			
	
	
	final DispRectangle backButton = new DispRectangle(10,10,100,40);
	final DispRectangle saveButton = new DispRectangle(SCALE_FACTOR*10,10,100,40);
	final DispRectangle loadButton = new DispRectangle(SCALE_FACTOR*10 + 110,10,100,40);
	final DispRectangle switchButton = new DispRectangle(10,60,100,40);
	
	private DispRectangle tableHighlight;
	private DispCircle studentHighlight;
	
	private LoadFile loadFile = new LoadFile();

	public FloorPlan() {
		super("Floor Plan");
		this.disp = new Display();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(this.disp);
		this.setSize(1000,1000);

		this.requestFocusInWindow();
		//this.setVisible(true);

		this.tableShapes = new ArrayList<DispRectangle>(0);
		this.studentShapes = new ArrayList<DispStudent>(0);
		this.uiShapes = new ArrayList<Shape>(0);
		this.switchUIShapes = new ArrayList<Shape>(0);
		
		studentHighlight = new DispCircle();
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
		double determinedX = 0;
		double determinedY = 0;

		for (int i = 0; i < tables.size(); i++) {
			DispRectangle tableCreation = new DispRectangle();
			tableCreation.setHeight(2*SCALE_FACTOR);
			tableCreation.setWidth(tableSize*SCALE_FACTOR/2); 

			if (i == 0) {
				determinedX = SCALE_FACTOR*10;
				determinedY = 100;
			} else {
				determinedX = tableShapes.get(i - 1).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
				determinedY = tableShapes.get(i - 1).getY();
				if (determinedX > SCREEN_SIZE.getWidth() - 100 - tableSize*SCALE_FACTOR/2) {
					determinedX = SCALE_FACTOR*10;
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

		public Display() {
			this.mouseListener = new MyMouseListener();
			this.addMouseListener(this.mouseListener);
			this.addMouseMotionListener(this.mouseListener);
			this.setBackground(LIGHT_GRAY); 
			
			this.state = UIState.STATE_VIEWING;
			
			switchButton.setPrivateColor(Color.ORANGE);
			backButton.setPrivateColor(Color.YELLOW);
			
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setDoubleBuffered(true);

			for (int i = 0; i < studentShapes.size(); i++) {
				studentShapes.get(i).draw(g, Color.BLUE);    
			}

			for (int i = 0; i < tableShapes.size(); i++) {
				tableShapes.get(i).draw(g, IP_PURPLE);
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

			if (this.state == UIState.STATE_TABLE_MOVING) {
				
			}
			
			
			Point mousePos = this.mouseListener.getPos();

			
			
			for (int i = 0; i < studentShapes.size(); i++) {
				if (studentShapes.get(i).getBoundingBox().contains(mousePos)) {
					DispStudent currStudent = studentShapes.get(i);

					DispRectangle infoBox = new DispRectangle();
					infoBox.setX(currStudent.getX() - SCALE_FACTOR*4 - 2);
					infoBox.setY(currStudent.getY() - SCALE_FACTOR*2 - 2);
					infoBox.setWidth(SCALE_FACTOR*4);
					infoBox.setHeight(SCALE_FACTOR*2);
					infoBox.draw(g,Color.MAGENTA);

					//g.fillOval((int)currStudent.getX(),(int)currStudent.getY(),(int)currStudent.getRadius(),(int)currStudent.getRadius());
					
					currStudent.draw(g,Color.MAGENTA);
					
					g.setColor(Color.BLUE); 

					g.fillOval((int)currStudent.getX() + 3,(int)currStudent.getY() + 3,
							(int)currStudent.getRadius() - 6,(int)currStudent.getRadius() - 6);

					g.drawString(currStudent.getOriginalStudent().getName(),(int)infoBox.getX() + 5,(int)infoBox.getY() + 15);
				}
				
				
				/*
    g.setColor(Color.MAGENTA);
    Rectangle current = studentShapes.get(i).getBoundingBox();
    g.fillRect((int)current.getX(),(int)current.getY(),(int)current.getWidth(),(int)current.getHeight());
				 */
			}
			
			if (this.state != UIState.STATE_TABLE_MOVING) {
				for (int i = 0; i < tableShapes.size(); i++) {
					if (tableShapes.get(i).getBoundingBox().contains(mousePos)) {
	
						DispRectangle currTable = tableShapes.get(i);
	
						DispRectangle infoBox = new DispRectangle();
						infoBox.setX(currTable.getX() - SCALE_FACTOR*4 - 2);
						infoBox.setY(currTable.getY() - SCALE_FACTOR*2 - 2);
						infoBox.setWidth(SCALE_FACTOR*4);
						infoBox.setHeight(SCALE_FACTOR*2);
						infoBox.draw(g,Color.MAGENTA);
	
						//g.fillRect((int)currTable.getX(),(int)currTable.getY(),(int)currTable.getWidth(),(int)currTable.getHeight());
						
						currTable.draw(g,Color.MAGENTA);
						g.setColor(IP_PURPLE);
						
						g.fillRect((int)currTable.getX() + 3,(int)currTable.getY() + 3,
								(int)currTable.getWidth() - 6,(int)currTable.getHeight() - 6);
						g.drawString("Table " + Integer.toString(i),(int)infoBox.getX() + 5,(int)infoBox.getY() + 15);
	
					}
				}
			} else if (this.state == UIState.STATE_TABLE_MOVING) {
				for (int i = 0; i < switchUIShapes.size(); i = i + 2) {
					DispRectangle currTable = (DispRectangle)switchUIShapes.get(i);
					
					if (currTable.getReferenceNumber() == 1) { 					
						if (currTable.getBoundingBox().contains(mousePos)) {
								
							DispRectangle infoBox = new DispRectangle();
							infoBox.setX(currTable.getX() - SCALE_FACTOR*4 - 2);
							infoBox.setY(currTable.getY() - SCALE_FACTOR*2 - 2);
							infoBox.setWidth(SCALE_FACTOR*4);
							infoBox.setHeight(SCALE_FACTOR*2);
							infoBox.draw(g,Color.MAGENTA);
		
							//g.fillRect((int)currTable.getX(),(int)currTable.getY(),(int)currTable.getWidth(),(int)currTable.getHeight());
							
							//currTable.draw(g,Color.MAGENTA);
							g.setColor(Color.GREEN);
							
							g.fillRect((int)currTable.getX() + 10,(int)currTable.getY() + 10,
									(int)currTable.getWidth() - 20,(int)currTable.getHeight() - 20);
							g.drawString("Table " + Integer.toString(i),(int)infoBox.getX() + 5,(int)infoBox.getY() + 15);
		
						}
					} else if (currTable.getReferenceNumber() == -1){
						if (currTable.getBoundingBox().contains(mousePos)) {
							g.setColor(Color.GREEN);
							
							g.fillRect((int)currTable.getX() + 10,(int)currTable.getY() + 10,
									(int)currTable.getWidth() - 20,(int)currTable.getHeight() - 20);
						}
					}
				}							
			}

			if (mouseListener.clickPending())  {
				Point clickPos = mouseListener.getClick();
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

							System.out.println("yay!");
							//DispCircle highlight = new DispCircle();

							studentHighlight.setX(studentShapes.get(i).getX());
							studentHighlight.setY(studentShapes.get(i).getY());
							studentHighlight.setRadius(studentShapes.get(i).getRadius());
							studentHighlight.setPrivateColor(Color.YELLOW);
							studentHighlight.setReferenceNumber(1);

							uiShapes.add(studentHighlight);        
							uiShapes.add(backButton);
							uiShapes.add(switchButton);
						}
					}

					for (int i = 0; i < tableShapes.size(); i++) {
						if (tableShapes.get(i).getBoundingBox().contains(clickPos)) {

							this.state = UIState.STATE_TABLE_SELECTED;

							//DispRectangle highlight = new DispRectangle();

							tableHighlight.setX(tableShapes.get(i).getX());
							tableHighlight.setY(tableShapes.get(i).getY());
							tableHighlight.setWidth(tableShapes.get(i).getWidth());
							tableHighlight.setHeight(tableShapes.get(i).getHeight());
							tableHighlight.setPrivateColor(Color.YELLOW);


							tableHighlight.setReferenceNumber(1);

							uiShapes.add(tableHighlight);        
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
					}  else if (switchButton.getBoundingBox().contains(clickPos)) {
						
						uiShapes.remove(switchButton);
						
						switch (this.state) {						
						case STATE_STUDENT_SELECTED:
							this.state = UIState.STATE_STUDENT_MOVING;
							break;
						case STATE_TABLE_SELECTED:
							this.state = UIState.STATE_TABLE_MOVING;							
							
							int tableSize =  (int)tableShapes.get(0).getWidth()*2;
							tableSize = tableSize/20;
							System.out.println(tableSize);
							double determinedX,determinedY;
							
							for (int i = 0; i < tableShapes.size(); i++) {
								DispRectangle uiCreation = tableShapes.get(i);
								uiCreation.setReferenceNumber(1);
								uiCreation.setPrivateColor(Color.GREEN);
								
								DispRectangle uiShade = new DispRectangle((int)uiCreation.getX() + 3,(int)uiCreation.getY() + 3,
										(int)uiCreation.getWidth() - 6,(int)uiCreation.getHeight() - 6);
								
								uiShade.setPrivateColor(IP_PURPLE);
								
								switchUIShapes.add(0,uiCreation);
								switchUIShapes.add(1,uiShade);
							}
							
							for (int i = 0; i < 40; i++) {
								
								DispRectangle uiCreation = new DispRectangle();
								uiCreation.setHeight(2*SCALE_FACTOR);
								uiCreation.setWidth(tableSize*SCALE_FACTOR/2); 
	
								if (i == 0) {
									determinedX = tableShapes.get(tableShapes.size() - 1).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
									determinedY = tableShapes.get(tableShapes.size() - 1).getY();
									if (determinedX > SCREEN_SIZE.getWidth() - 100 - tableSize*SCALE_FACTOR/2) {
										determinedX = SCALE_FACTOR*10;
										determinedY = determinedY + SCALE_FACTOR*6;
									}
								} else {
									determinedX = switchUIShapes.get(0).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
									determinedY = switchUIShapes.get(0).getY();
									if (determinedX > SCREEN_SIZE.getWidth() - 100 - tableSize*SCALE_FACTOR/2) {
										determinedX = SCALE_FACTOR*10;
										determinedY = determinedY + SCALE_FACTOR*6;
									}
								}
	
								uiCreation.setX(determinedX);
								uiCreation.setY(determinedY);
								uiCreation.setPrivateColor(Color.GREEN);
								uiCreation.setReferenceNumber(-1);
	
								switchUIShapes.add(0,uiCreation);
								
								
								DispRectangle uiShade = new DispRectangle((int)uiCreation.getX() + 3,(int)uiCreation.getY() + 3,
										(int)uiCreation.getWidth() - 6,(int)uiCreation.getHeight() - 6);
								
								uiShade.setPrivateColor(LIGHT_GRAY);
								for (int j = 0; j < tableShapes.size(); j++) {
									if (tableShapes.get(j).getBoundingBox().intersects(uiShade.getBoundingBox())) {
										uiShade.setPrivateColor(IP_PURPLE);
									}
								}
								
								switchUIShapes.add(1,uiShade);
							
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
						uiShapes.clear();
						uiShapes.add(backButton);
						uiShapes.add(switchButton);
						uiShapes.add(saveButton);
						uiShapes.add(loadButton);
						uiShapes.add(studentHighlight);
					}
					
				} else if (this.state ==  UIState.STATE_TABLE_MOVING) {
					if (backButton.getBoundingBox().contains(clickPos)) {
						uiShapes.add(switchButton);
						this.state = UIState.STATE_TABLE_SELECTED;
						//uiShapes.clear();
						switchUIShapes.clear();
//						uiShapes.add(backButton);
//						uiShapes.add(switchButton);
//						uiShapes.add(saveButton);
//						uiShapes.add(loadButton);
//						uiShapes.add(tableHighlight);
					}
				}
			}
			
//			if (this.state == UIState.STATE_TABLE_MOVING) {
//				for (int i = 0; i < tableShapes.size(); i++) {
//					
//					tableShapes.get(i).draw(g,Color.GREEN);
//					
//					g.setColor(IP_PURPLE);
//					g.fillRect((int)tableShapes.get(i).getX() + 3,(int)tableShapes.get(i).getY() + 3,
//							(int)tableShapes.get(i).getWidth() - 6,(int)tableShapes.get(i).getHeight() - 6);
//					
//					
//				}
//			}
						
			
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

		public Point getClick() {
			return new Point(clickX, clickY);
		}

		public Point getPos() {
			return new Point(x, y);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("System Operational");
	}
}
