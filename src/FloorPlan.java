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
	private SidePanel sidePnl;
	private ArrayList<DispRectangle> tableShapes;
	private ArrayList<DispStudent> studentShapes;
	private ArrayList<Shape> uiShapes;


	private int MAX_TOP = 0;
	private int MAX_LEFT = 0;
	private int MAX_BOTTOM = 2000;
	private int MAX_RIGHT = 2000;

	final DispRectangle backButton = new DispRectangle(10,10,100,40);
	final DispRectangle saveButton = new DispRectangle(SCALE_FACTOR*10,10,100,40);
	final DispRectangle loadButton = new DispRectangle(SCALE_FACTOR*10 + 110,10,100,40);
	final DispRectangle switchButton = new DispRectangle(10,60,100,40);

	private DispCircle selectedStudent;

	private DispStudent focusedStudent;
	private DispTable focusedTable;

	private LoadFile loadFile = new LoadFile();

	// For communication between panels


	private boolean saveButtonActive;
	private boolean loadButtonActive;

	private boolean backButtonActive;
	private boolean backButtonVisible;

	private boolean switchButtonActive;
	private boolean switchButtonVisible;


	public FloorPlan() {
		super("Floor Plan");
		this.disp = new Display();
		this.sidePnl = new SidePanel();

		Dimension sideBarSize = new Dimension(200,(int) SCREEN_SIZE.getHeight());

		sidePnl.setPreferredSize(sideBarSize);
		sidePnl.setBackground(Color.ORANGE); 

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(this.disp, BorderLayout.CENTER);
		this.add(sidePnl,BorderLayout.WEST);
		this.setSize((int)SCREEN_SIZE.getWidth(),(int)SCREEN_SIZE.getHeight());

		this.requestFocusInWindow();

		this.tableShapes = new ArrayList<DispRectangle>(0);
		this.studentShapes = new ArrayList<DispStudent>(0);
		this.uiShapes = new ArrayList<Shape>(0);

		selectedStudent = new DispCircle();

		focusedStudent = new DispStudent();
		focusedTable = new DispTable();

		saveButton.setPrivateColor(Color.CYAN);
		loadButton.setPrivateColor(Color.CYAN);

		uiShapes.add(saveButton);
		uiShapes.add(loadButton);
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
		System.out.println(MAX_RIGHT);
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

	private class SidePanel extends JPanel {

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

		SidePanel() {
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

			saveButton2.draw(g,Color.CYAN);
			loadButton2.draw(g,Color.CYAN);

			if (disp.getUIState() != UIState.STATE_VIEWING) {
				backButton2.draw(g,Color.YELLOW);
			}

			if ((disp.getUIState() == UIState.STATE_STUDENT_SELECTED) || (disp.getUIState() == UIState.STATE_TABLE_SELECTED)) {
				switchButton2.draw(g,Color.GREEN);
			}

			g.setColor(Color.BLACK);

			if (focusedStudent.isHovered()) {
				Student dataStudent = focusedStudent.getOriginalStudent();

				g.drawString(dataStudent.getName(),10,400);

			} else if (focusedTable.isHovered()) {
				if (focusedTable.isReal()) {
					Table dataTable = focusedTable.getOriginalTable();


					g.drawString(Integer.toString(dataTable.getSize()),10,400);
				}
			}

			if (mouseListener2.clickPending())  {
				Point clickPos = mouseListener2.getClick();		     

				this.clickPending = true;

				mouseListener2.clickHandled();

				if (backButton2.getBoundingBox().contains(clickPos)) {
					this.backButtonState = true;
					System.out.println("Detected 2!");
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
			Display sideBar = new Display();

			Dimension sideBarSize = new Dimension(200,(int) SCREEN_SIZE.getHeight());

			sideBar.setPreferredSize(sideBarSize);
			sideBar.setBackground(Color.ORANGE); 

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.add(this.disp, BorderLayout.CENTER);
			this.add(sideBar,BorderLayout.WEST);
			this.setSize(1000,1000);

			this.requestFocusInWindow();
			//this.setVisible(true);
			for (int i = 0; i < studentShapes.size(); i++) {
				studentShapes.get(i).drawObject(g);    
			}

			for (int i = 0; i < tableShapes.size(); i++) {
				((DispTable) tableShapes.get(i)).drawObject(g);
			}

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
				mousePos.x = (int) (mousePos.x * mouseListener.zoomScale + camX);
				mousePos.y = (int) (mousePos.y * mouseListener.zoomScale + camY);

				if (mouseListener.isDragging() == false) {
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

								g.fillRect((int)currTable.getX(),(int)currTable.getY(),(int)currTable.getWidth(),(int)currTable.getHeight());

								g.setColor(IP_PURPLE); 

								g.fillRect((int)currTable.getX() + 3,(int)currTable.getY() + 3,
										(int)currTable.getWidth() - 6,(int)currTable.getHeight() - 6);

								g.drawString("Table " + Integer.toString(i),(int)infoBox.getX() + 5,(int)infoBox.getY() + 15);


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

									DispRectangle tableSearch = new DispRectangle(studentHighlight.getX() - 10,studentHighlight.getY() - 10,
											studentHighlight.getRadius() + 20, studentHighlight.getRadius() + 20);


									for (int i = 0; i < tableShapes.size(); i++) {
										DispRectangle currTable = tableShapes.get(i); 

										if (tableSearch.getBoundingBox().intersects(currTable.getBoundingBox())) {
											i = tableShapes.size();

											DispRectangle studentSearch = new DispRectangle(currTable.getX() - 10,currTable.getY() - 10,
													currTable.getWidth() + 20,currTable.getHeight() + 20);

											for (int j = 0; j < studentShapes.size(); j++) {
												if (studentSearch.getBoundingBox().intersects(studentShapes.get(j).getBoundingBox())) {

													DispCircle uiCreation = new DispCircle();
													DispCircle uiShade = new DispCircle();

													uiCreation.setX(studentShapes.get(j).getX());
													uiShade.setX(studentShapes.get(j).getX() + 4);
													uiCreation.setY(studentShapes.get(j).getY());
													uiShade.setY(studentShapes.get(j).getY() + 4);

													uiCreation.setRadius(studentShapes.get(j).getRadius());
													uiShade.setRadius(studentShapes.get(j).getRadius() - 8);

													uiCreation.setPrivateColor(Color.GREEN);
													uiShade.setPrivateColor(Color.BLUE);


													switchUIShapes.add(uiCreation);
													switchUIShapes.add(uiShade);
												}

											}

										}
									}



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
								switchUIShapes.clear();
							}

						} else if (this.state ==  UIState.STATE_TABLE_MOVING) {
							if (backButton.getBoundingBox().contains(clickPos)) {
								uiShapes.add(switchButton);
								this.state = UIState.STATE_TABLE_SELECTED;
								switchUIShapes.clear();
							}
						}
					}

					//    if (this.state == UIState.STATE_TABLE_MOVING) {
					//     for (int i = 0; i < tableShapes.size(); i++) {
					//      tableShapes.get(i).draw(g,Color.GREEN);
					//     }
					//    }
				}

				//   if (this.state == UIState.STATE_TABLE_MOVING) {
				//    for (int i = 0; i < tableShapes.size(); i++) {
				//     
				//     tableShapes.get(i).draw(g,Color.GREEN);
				//     
				//     g.setColor(IP_PURPLE);
				//     g.fillRect((int)tableShapes.get(i).getX() + 3,(int)tableShapes.get(i).getY() + 3,
				//       (int)tableShapes.get(i).getWidth() - 6,(int)tableShapes.get(i).getHeight() - 6);
				//     
				//     
				//    }
				//   }


				// this.repaint();

			}

			public void updateCamera(Graphics g) {
				zooming(g);
				panning(g);
			}

			public void zooming(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Dimension d = this.getSize();
				g2.translate(d.width/2, d.height/2);
				g2.scale(mouseListener.zoomScale, mouseListener.zoomScale);
				g2.translate(-d.width/2, -d.height/2);
			}

			public void panning(Graphics g) {
				if (mouseListener.isDragging()) {
					dx = mouseListener.releaseX - mouseListener.clickX;
					dy = mouseListener.releaseY - mouseListener.clickY;
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

		private class MyMouseListener implements MouseInputListener, MouseMotionListener, MouseWheelListener {
			private int x;
			private int y;
			private int clickX;
			private int clickY;
			private int releaseX;
			private int releaseY;
			private double zoomScale = 1;
			private boolean clickHandled;
			private boolean isDragging = false;

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
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				clickX = e.getX();
				clickY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				releaseX = e.getX();
				releaseY = e.getY();
				this.isDragging = false;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				releaseX = e.getX();
				releaseY = e.getY();
				this.isDragging = true;
			}

			public Point getClick() {
				return new Point(clickX, clickY);
			}

			public Point getPos() {
				return new Point(x, y);
			}

			public boolean isDragging() {
				return this.isDragging;
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int notches = e.getWheelRotation();
				if (notches < 0) { // mouse wheel moved up
					for (int i = notches; i < 0; i++) {
						zoomScale += 0.1;
					}
				} else if (notches > 0) { // mouse wheel moved down
					for (int i = notches; i > 0; i--) {
						zoomScale -= 0.1;
					}
				}
				if (zoomScale < 1) {
					zoomScale = 1;
				} else if (zoomScale > 4) {
					zoomScale = 4;
				}
			}
		}

		public static void main(String[] args) {
			System.out.println("System Operational");
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

					focusedStudent = studentShapes.get(i);
					focusedTable.setHovered(false);

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

					focusedTable = (DispTable) tableShapes.get(i);
					focusedStudent.setHovered(false);

				} else {
					tableShapes.get(i).setHovered(false); 
				}
			}



			if ((mouseListener.clickPending()) || (sidePnl.anyPending()))  {
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
					System.out.println(Boolean.toString(sidePnl.backButtonPending()));
					if (sidePnl.backButtonPending()) {
						System.out.println("yay?");
						sidePnl.handleAll();  
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

						for (int i = 0; i < studentShapes.size(); i++) {
							studentShapes.get(i).setHighlighted(false);   
						}

					}

				} else if (this.state ==  UIState.STATE_TABLE_MOVING) {
					if (backButton.getBoundingBox().contains(clickPos)) {
						uiShapes.add(switchButton);
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
