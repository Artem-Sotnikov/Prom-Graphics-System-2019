import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static constant.Constants.*;

public class FloorPlan extends JFrame {
	private static final long serialVersionUID = 1L;

	private Display disp;
	private SidePanel sidePnl;
	private ArrayList<DispRectangle> tableShapes;
	private ArrayList<DispStudent> studentShapes;

	private int MAX_TOP = 0;
	private int MAX_LEFT = 0;
	private int MAX_BOTTOM = 2000;
	private int MAX_RIGHT = 2000;

	final DispRectangle backButton = new DispRectangle(10,10,100,40);
	final DispRectangle saveButton = new DispRectangle(SCALE_FACTOR*10,10,100,40);
	final DispRectangle loadButton = new DispRectangle(SCALE_FACTOR*10 + 110,10,100,40);
	final DispRectangle switchButton = new DispRectangle(10,60,100,40);

	private DispStudent selectedStudent;
	private int selectedStudentIdx;
	private DispTable selectedTable;
	private int selectedTableIdx;
	
	private DispStudent focusedStudent;
	private DispTable focusedTable;

	private LoadFile loadFile = new LoadFile();

	public FloorPlan() {
		super("Floor Plan");
		this.disp = new Display();
		this.sidePnl = new SidePanel();

		Dimension sideBarSize = new Dimension(200,(int) SCREEN_SIZE.getHeight());

		sidePnl.setPreferredSize(sideBarSize);
		sidePnl.setBackground(DARK_GRAY); 

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(this.disp, BorderLayout.CENTER);
		this.add(sidePnl,BorderLayout.WEST);
		this.setSize((int)SCREEN_SIZE.getWidth(),(int)SCREEN_SIZE.getHeight());

		this.requestFocusInWindow();

		this.tableShapes = new ArrayList<DispRectangle>(0);
		this.studentShapes = new ArrayList<DispStudent>(0);

		selectedStudent = new DispStudent();
		selectedTable = new DispTable();

		focusedStudent = new DispStudent();
		focusedTable = new DispTable();
	}

	public void displayFloorPlan() {

		this.setVisible(true);

		while (true) {  
			this.disp.repaint();
			this.sidePnl.repaint();
		}  
	}

	public void exit() {
		this.dispose();
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
		//System.out.println(MAX_RIGHT);
		this.MAX_BOTTOM = this.MAX_RIGHT;


		double determinedX = 0;
		double determinedY = 0;

		for (int i = 0; i < tables.size(); i++) {
			DispTable tableCreation = new DispTable();

			tableCreation.setReal(true);
			tableCreation.setOriginalTable(tables.get(i));

			tableCreation.setHeight(2*SCALE_FACTOR);
			tableCreation.setWidth(tableSize*SCALE_FACTOR/2); 

			if (i == 0) {
				determinedX = SCALE_FACTOR*10;
				determinedY = 100;
			} else {
				determinedX = tableShapes.get(i - 1).getX() + tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;
				determinedY = tableShapes.get(i - 1).getY();
				//System.out.println(determinedX);
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

			//System.out.println(determinedY);

			if (determinedY > (MAX_BOTTOM - SCALE_FACTOR*10)) {
				boardExhausted = true;
			} else {
				tableShapes.add(tableCreation);
			}


		}

	}

	public void generateFloorPlan(ArrayList<Table> tables, String config) {
		if (config == "ROUND TABLES") {
			int tableSize = tables.get(0).getSize(); 
			int distToNextTable = tableSize*SCALE_FACTOR/2 + SCALE_FACTOR*2;

			this.MAX_RIGHT = (int) ((Math.ceil(Math.sqrt(tables.size())))*((tableSize/2) + 2)*SCALE_FACTOR + 200);
			//System.out.println(MAX_RIGHT);
			this.MAX_BOTTOM = this.MAX_RIGHT;

			double determinedX = 0;
			double determinedY = 0;

			boolean offset = false;

			for (int i = 0; i < tables.size(); i++) {
				DispTable tableCreation = new DispTable();

				tableCreation.setReal(true);
				tableCreation.setRound(true);
				tableCreation.setOriginalTable(tables.get(i));

				tableCreation.setHeight(tableSize*SCALE_FACTOR/3);
				tableCreation.setWidth(tableSize*SCALE_FACTOR/3); 

				if (i == 0) {
					determinedX = SCALE_FACTOR*10;
					determinedY = 100;
				} else {
					determinedX = tableShapes.get(i - 1).getX() + distToNextTable;
					determinedY = tableShapes.get(i - 1).getY();

					//System.out.println(determinedX);

					if (determinedX > (this.MAX_RIGHT - SCALE_FACTOR*10 - tableSize*SCALE_FACTOR/2)) {
						determinedX = SCALE_FACTOR*10 + distToNextTable*(Math.cos(60*Math.PI/180));
						determinedY = determinedY +  distToNextTable*(Math.sin(60*Math.PI/180));

						if (offset) {
							determinedX = SCALE_FACTOR*10;
							offset = false;
						} else {
							offset = true;
						}

					}

				}

				tableCreation.setX(determinedX);
				tableCreation.setY(determinedY);

				tableShapes.add(tableCreation);

				double tableCenterX = determinedX + tableCreation.getHeight()/2;
				double tableCenterY = determinedY + tableCreation.getHeight()/2;


				for (int j = 0; j < tables.get(i).getStudents().size(); j++) {
					DispStudent studentCreation = new DispStudent();

					studentCreation.setReal(true);

					studentCreation.setRadius(SCALE_FACTOR - 2);

					double currentAngle = j*2*Math.PI/tableSize; 
					//System.out.println(currentAngle);

					studentCreation.setX(tableCenterX + tableCreation.getHeight()*Math.cos(currentAngle)/1.4 
							- studentCreation.getRadius()/2);
					studentCreation.setY(tableCenterY + tableCreation.getHeight()*Math.sin(currentAngle)/1.4
							- studentCreation.getRadius()/2);


					studentCreation.setOriginalStudent(tables.get(i).getStudents().get(j));
					studentShapes.add(studentCreation);
				}
			}
			
			boolean boardExhausted = false;

			while (!boardExhausted) {   

				DispTable tableCreation = new DispTable();

				tableCreation.setReal(false);
				tableCreation.setRound(true);

				tableCreation.setHeight(tableSize*SCALE_FACTOR/3);
				tableCreation.setWidth(tableSize*SCALE_FACTOR/3); 

				determinedX = tableShapes.get(tableShapes.size() - 1).getX() + distToNextTable;
				determinedY = tableShapes.get(tableShapes.size() - 1).getY();
				if (determinedX > MAX_RIGHT - SCALE_FACTOR*10 - tableSize*SCALE_FACTOR/3) {
					determinedX = SCALE_FACTOR*10 + distToNextTable*(Math.cos(60*Math.PI/180));
					determinedY = determinedY +  distToNextTable*(Math.sin(60*Math.PI/180));

					if (offset) {
						determinedX = SCALE_FACTOR*10;
						offset = false;
					} else {
						offset = true;
					}
				}


				tableCreation.setX(determinedX);
				tableCreation.setY(determinedY);

				//System.out.println(determinedY);

				if (determinedY > (MAX_BOTTOM - SCALE_FACTOR*10)) {
					boardExhausted = true;
				} else {
					tableShapes.add(tableCreation);
				}


			}
		}
	}

	private class SidePanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		final DispRectangle saveButton2 = new DispRectangle(10,10,100,40);
		final DispRectangle loadButton2 = new DispRectangle(10,60,100,40);
		final DispRectangle backButton2 = new DispRectangle(10,110,100,40);
		final DispRectangle switchButton2 = new DispRectangle(10,160,100,40); 

		private boolean loadButtonState;
		private boolean saveButtonState;
		private boolean backButtonState;
		private boolean switchButtonState;

		private boolean clickPending;

		private MyMouseListener mouseListener2;

		public SidePanel() {
			this.mouseListener2 = new MyMouseListener();
			this.addMouseListener(this.mouseListener2);
			this.addMouseMotionListener(this.mouseListener2);
			this.addMouseWheelListener(this.mouseListener2);

			this.handleAll();
		}

		public boolean anyPending() {
			return this.clickPending;
		}

		public boolean loadButtonPending() {
			return this.loadButtonState;
		}

		public boolean saveButtonPending() {
			return this.saveButtonState; 
		}

		public boolean backButtonPending() {
			return this.backButtonState;
		}

		public boolean switchButtonPending() {
			return this.switchButtonState;
		}

		public void handleAll() {
			this.clickPending = false;

			this.loadButtonState = false;
			this.saveButtonState = false;
			this.backButtonState = false;
			this.switchButtonState = false;
		}

		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			setDoubleBuffered(true);

			loadButton2.draw(g,Color.CYAN);
			saveButton2.draw(g,Color.CYAN);

			g.setColor(Color.BLACK);
			g.drawString("SAVE",(int)saveButton2.getX() + OFFSET_FACTOR,(int)saveButton2.getY() + OFFSET_FACTOR*3);
			g.drawString("LOAD",(int)loadButton2.getX() + OFFSET_FACTOR,(int)loadButton2.getY() + OFFSET_FACTOR*3);


			if (disp.getUIState() != UIState.STATE_VIEWING) {	   
				backButton2.draw(g,Color.YELLOW);
				g.setColor(Color.BLACK);
				g.drawString("BACK",(int)backButton2.getX() + OFFSET_FACTOR,(int)backButton2.getY() + OFFSET_FACTOR*3);
			}

			if ((disp.getUIState() == UIState.STATE_STUDENT_SELECTED) || (disp.getUIState() == UIState.STATE_TABLE_SELECTED)) {
				switchButton2.draw(g,Color.GREEN);
				g.setColor(Color.BLACK);
				g.drawString("SWITCH WITH",(int)switchButton2.getX() + OFFSET_FACTOR,(int)switchButton2.getY() + OFFSET_FACTOR*3);
			}
			
			g.setColor(Color.WHITE);
			g.fillRect(5,400 - OFFSET_FACTOR*3,190,2000);
			
			g.setColor(Color.BLACK);

			
			
			if (focusedStudent.isHovered()) {
				Student dataStudent = focusedStudent.getOriginalStudent();

				g.drawString("Student Name: " + dataStudent.getName(),10,400);

			} else if (focusedTable.isHovered()) {
				if (focusedTable.isReal()) {
					Table dataTable = focusedTable.getOriginalTable();


					g.drawString("Table size: " + Integer.toString(dataTable.getSize()),10,400);
				}
			}

			if (mouseListener2.clickPending())  {
				Point clickPos = mouseListener2.getClick();       

				this.clickPending = true;

				mouseListener2.clickHandled();

				if (backButton2.getBoundingBox().contains(clickPos)) {
					this.backButtonState = true;					
				} else if (switchButton2.getBoundingBox().contains(clickPos)) {
					this.switchButtonState = true;
				} else if (saveButton2.getBoundingBox().contains(clickPos)) {
					this.saveButtonState = true;
				} else if (loadButton2.getBoundingBox().contains(clickPos)) {
					this.loadButtonState = true;
				}
			}
		}
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

			//Draws borders
			g.fillRect(MAX_LEFT + 5,MAX_TOP + 5,MAX_RIGHT - 10,5);
			g.fillRect(MAX_LEFT + 5,MAX_TOP + 5,5,MAX_BOTTOM - 10);
			g.fillRect(MAX_LEFT + 10,MAX_BOTTOM - 10,MAX_RIGHT - 20,5);
			g.fillRect(MAX_RIGHT - 10,MAX_TOP + 5,5,MAX_BOTTOM - 10);

			//

			for (int i = 0; i < studentShapes.size(); i++) {
				studentShapes.get(i).drawObject(g);    
			}

			for (int i = 0; i < tableShapes.size(); i++) {
				((DispTable) tableShapes.get(i)).drawObject(g);
			}

			Point mousePos = this.mouseListener.getPos();
			mousePos.x = (int) (mousePos.x * mouseListener.getZoomScale() + camX);
			mousePos.y = (int) (mousePos.y * mouseListener.getZoomScale() + camY);

			boolean studentHovered = false;

			// Table hover is set and text box displayed directly
			if (mouseListener.isDragging() == false) {
				for (int i = 0; i < tableShapes.size(); i++) {
					if (tableShapes.get(i).getBoundingBox().contains(mousePos)) {

						tableShapes.get(i).setHovered(true);
						((DispTable) tableShapes.get(i)).drawBox(g,i);

						focusedTable = (DispTable) tableShapes.get(i);
						focusedStudent.setHovered(false);
						studentHovered = true;

					} else {
						tableShapes.get(i).setHovered(false); 
					}
				}

				// Student hover is set and text box displayed directly
				if (!studentHovered) {
					if (mouseListener.isDragging() == false) {
						for (int i = 0; i < studentShapes.size(); i++) {
							if (studentShapes.get(i).getBoundingBox().contains(mousePos)) {

								studentShapes.get(i).setHovered(true);
								studentShapes.get(i).drawBox(g); 

								focusedStudent = studentShapes.get(i);
								focusedTable.setHovered(false);

							} else {
								studentShapes.get(i).setHovered(false); 
							}
						}
					}
				}

				if ((mouseListener.clickPending()) || (sidePnl.anyPending()))  {
					Point clickPos = mouseListener.getClick();
					clickPos.x += camX;
					clickPos.y += camY;
					mouseListener.clickHandled();  

					if (sidePnl.anyPending()) {
						clickPos = new Point(0,0);
					}

					// click save or load button
					if (sidePnl.saveButtonPending()) {
						saveShapesToFile();
					} else if (sidePnl.loadButtonPending()) {
						loadShapesFromFile();
					}

					if (this.state == UIState.STATE_VIEWING) {
						for (int i = 0; i < studentShapes.size(); i++) {
							if (studentShapes.get(i).getBoundingBox().contains(clickPos)) {

								this.state = UIState.STATE_STUDENT_SELECTED;

								studentShapes.get(i).setSelected(true);
								selectedStudent = studentShapes.get(i);
								selectedStudentIdx = i;

							}
						}

						for (int i = 0; i < tableShapes.size(); i++) {
							if (tableShapes.get(i).getBoundingBox().contains(clickPos)) {

								this.state = UIState.STATE_TABLE_SELECTED;

								tableShapes.get(i).setSelected(true);        
								selectedTable = (DispTable) tableShapes.get(i);
								selectedTableIdx = i;

							}
						}

					} else if (this.state == UIState.STATE_STUDENT_SELECTED || this.state == UIState.STATE_TABLE_SELECTED) {

						//System.out.println(Boolean.toString(sidePnl.backButtonPending()));

						if (sidePnl.backButtonPending()) {       

							this.state = UIState.STATE_VIEWING;

							for (int i = 0; i < studentShapes.size(); i++) {
								studentShapes.get(i).setSelected(false);
							}

							for (int i = 0; i < tableShapes.size(); i ++) {
								tableShapes.get(i).setSelected(false);
							}

						} else if (sidePnl.switchButtonPending()) {


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

						
						for (int i = 0; i < studentShapes.size(); i++) {
							if (studentShapes.get(i).getBoundingBox().contains(clickPos)) {
								if (studentShapes.get(i).isHighlighted()) {
									
									double futureX = studentShapes.get(i).getX();
									double futureY = studentShapes.get(i).getY();
									
									studentShapes.get(i).setX(selectedStudent.getX());
									studentShapes.get(i).setY(selectedStudent.getY());
									
									studentShapes.get(studentShapes.lastIndexOf(selectedStudent)).setX(futureX);
									studentShapes.get(studentShapes.lastIndexOf(selectedStudent)).setY(futureY);
									
									this.state = UIState.STATE_STUDENT_SELECTED;

									for (int j = 0; j < studentShapes.size(); j++) {
										studentShapes.get(j).setHighlighted(false);   
									}
									
									
								}								
							} 
						}
						
						
						
						if (sidePnl.backButtonPending()) {

							this.state = UIState.STATE_STUDENT_SELECTED;

							for (int i = 0; i < studentShapes.size(); i++) {
								studentShapes.get(i).setHighlighted(false);   
							}
						}

					} else if (this.state ==  UIState.STATE_TABLE_MOVING) {
						System.out.println("Table inital hit!");
						for (int i = 0; i < tableShapes.size(); i++) {
							if (tableShapes.get(i).getBoundingBox().contains(clickPos)) {
								if (tableShapes.get(i).isHighlighted()) {
									
									System.out.println("Table hit!");
									
									DispRectangle currTable = selectedTable;									
									DispRectangle futureTable = tableShapes.get(i);
																																	
									double dx = futureTable.getX() - 
											currTable.getX();
									double dy = futureTable.getY() - 
											currTable.getY();
									
									DispRectangle studentSearch = new DispRectangle(currTable.getX() - 10,currTable.getY() - 10,
											currTable.getWidth() + 20,currTable.getHeight() + 20);

									for (int j = 0; j < studentShapes.size(); j++) {
										if (studentSearch.getBoundingBox().intersects(studentShapes.get(j).getBoundingBox())) {

											studentShapes.get(j).setHighlighted(true);
											studentShapes.get(j).setX(studentShapes.get(j).getX() + dx);
											studentShapes.get(j).setY(studentShapes.get(j).getY() + dy);
											

										}           
									}
									
									DispRectangle studentSearch2 = new DispRectangle(futureTable.getX() - 10,futureTable.getY() - 10,
											futureTable.getWidth() + 20,futureTable.getHeight() + 20);
									
									for (int j = 0; j < studentShapes.size(); j++) {
										if (studentSearch2.getBoundingBox().intersects(studentShapes.get(j).getBoundingBox())) {
											if (!(studentShapes.get(j).isHighlighted())) {

												studentShapes.get(j).setX(studentShapes.get(j).getX() - dx);
												studentShapes.get(j).setY(studentShapes.get(j).getY() - dy);
												
											} else {
												studentShapes.get(j).setHighlighted(false);
											}

										}           
									}
									
									tableShapes.get(i).setX(currTable.getX());
									tableShapes.get(i).setY(currTable.getY());
									
									tableShapes.get(selectedTableIdx).setX(tableShapes.get(selectedTableIdx).getX() + dx);
									tableShapes.get(selectedTableIdx).setY(tableShapes.get(selectedTableIdx).getY() + dy);
									
									for (int j = 0; j < tableShapes.size(); j++) {
										tableShapes.get(j).setHighlighted(false);
									}
									
									this.state = UIState.STATE_TABLE_SELECTED;								
									
									
								}								
							} 
						}
						
						
						
						if (sidePnl.backButtonPending()) {

							this.state = UIState.STATE_TABLE_SELECTED;

							for (int i = 0; i < tableShapes.size(); i++) {
								tableShapes.get(i).setHighlighted(false);   
							}
						}
					} 

					sidePnl.handleAll();

				}
			} 
		}

		public void updateCamera(Graphics g) {
			zooming(g);
			panning(g);
		}

		public UIState getUIState() {
			return this.state;
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
