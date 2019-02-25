import static constant.Constants.DARK_GRAY;
import static constant.Constants.LIGHT_GRAY;
import static constant.Constants.OFFSET_FACTOR;
import static constant.Constants.SCALE_FACTOR;
import static constant.Constants.SCREEN_SIZE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FloorPlan: This class will generate and display a floor plan.
 * 
 * @author Artem
 * @author Anthony
 */
public class FloorPlan extends JFrame {
	private static final long serialVersionUID = 1L;

	private Display disp;
	private SidePanel sidePnl;
	private ArrayList<DispTable> tableShapes;
	private ArrayList<DispStudent> studentShapes;

	private int MAX_TOP = 0;
	private int MAX_LEFT = 0;
	private int MAX_BOTTOM = 2000;
	private int MAX_RIGHT = 2000;

	private DispStudent focusedStudent;
	private DispTable focusedTable;

	private DispStudent selectedStudent;

	private DispTable selectedTable;
	private int selectedTableIdx;

	private JFileChooser chooser;

	private JPanel promptPanel;

	private JTextField maxRightField = new JTextField(5);
	private JTextField maxBottomField = new JTextField(5);

	private boolean sizeSet;
	private JLabel currentSizeLabel;
	private JPanel infoLabel;


	private LoadFile loadFile = new LoadFile("src/savefiles/default.txt");

	/**
	 * Class constructor.
	 */
	public FloorPlan() {
		super("Floor Plan");
		this.disp = new Display();
		this.sidePnl = new SidePanel();

		Dimension sideBarSize = new Dimension(200, (int) SCREEN_SIZE.getHeight());

		sidePnl.setPreferredSize(sideBarSize);
		sidePnl.setBackground(DARK_GRAY);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(this.disp, BorderLayout.CENTER);
		this.add(sidePnl, BorderLayout.WEST);
		this.setSize((int) SCREEN_SIZE.getWidth(), (int) SCREEN_SIZE.getHeight());

		// this.requestFocusInWindow();

		this.tableShapes = new ArrayList<DispTable>(0);
		this.studentShapes = new ArrayList<DispStudent>(0);

		selectedStudent = new DispStudent();
		selectedTable = new DispTable();

		focusedStudent = new DispStudent();
		focusedTable = new DispTable();

		this.chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		chooser.setFileFilter(filter);

		currentSizeLabel = new JLabel();

		promptPanel = new JPanel();
		promptPanel.add(currentSizeLabel);
		promptPanel.add(new JLabel("Enter Max Width Size"));
		promptPanel.add(maxRightField);
		promptPanel.add(Box.createHorizontalStrut(15)); // a spacer
		promptPanel.add(new JLabel("Enter Max Height Size"));
		promptPanel.add(maxBottomField);

		sizeSet = false;

		infoLabel = new JPanel();
		infoLabel.add(new JLabel("Your layout has been sucessfully saved to file, and can now be retrived in future with the 'load' button"));
	}


	/**
	 * This method will check if the string can be cast to a valid integer.
	 * 
	 * @param String input
	 */

	private boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method will display the floor plan using repaint.
	 */
	public void displayFloorPlan() {

		this.setVisible(true);

		while (true) {

			this.disp.repaint();
			this.sidePnl.repaint();

			boolean flag = false;

			if (disp.state == UIState.STATE_RESIZING) {
				do {
					currentSizeLabel.setText("Current Dimensions Are: " + Integer.toString(MAX_RIGHT) + " by "
							+ Integer.toString(MAX_BOTTOM) + "\n");
					int result = JOptionPane.showConfirmDialog(null, promptPanel, "Please Enter Room Size",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						if ((isInteger(maxRightField.getText())) && (isInteger(maxBottomField.getText()))) {
							if (Integer.parseInt(maxRightField.getText()) > 400
									&& Integer.parseInt(maxRightField.getText()) > 400) {
								this.sizeSet = true;
								this.MAX_RIGHT = Integer.parseInt(maxRightField.getText());
								this.MAX_BOTTOM = Integer.parseInt(maxRightField.getText());

								this.regenerateFloorPlan("round");

								disp.state = UIState.STATE_VIEWING;
								flag = false;
							} else {
								JOptionPane.showMessageDialog(null, "Please enter a room size big enough");
								flag = true;
							}
						} else {
							JOptionPane.showMessageDialog(null, "Please enter valid integers");
							flag = true;
						}
					} else {
						disp.state = UIState.STATE_VIEWING;
						flag = false;
					}
				} while (flag);
			}

		}
	}

	/**
	 * This method will save a floor plan to a file.
	 */
	public void saveFloorPlan() {
		loadFile.setSaveFile(new SaveFile(tableShapes, studentShapes));
		loadFile.save();
		//  
	}

	/**
	 * This method will save a floor plan to a specified file.
	 */
	public void saveAsFloorPlan() {
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String fileName = chooser.getSelectedFile().getPath();
			this.loadFile.setFileName(fileName);
			this.loadFile.setSaveFile(new SaveFile(tableShapes, studentShapes));
			loadFile.save();
		}
	}

	/**
	 * This method will load a floor plan from a file.
	 * 
	 * @param boolean initial
	 */
	public void loadFloorPlan(boolean initial) {
		if (initial == false) {
			saveFloorPlan();
		}
		chooseFile(initial);
		loadFile.load();
		SaveFile saveFile = loadFile.getSaveFile();
		tableShapes = saveFile.getTableList();
		studentShapes = saveFile.getStudentList();
	}

	/**
	 * This method will choose a file.
	 * 
	 * @param boolean initial
	 */
	public void chooseFile(boolean initial) {
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String fileName = chooser.getSelectedFile().getPath();
			this.loadFile = new LoadFile(fileName);
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			if (initial == true) {
				System.exit(0);
			}
		}
	}

	/**
	 * This method will generate a floor plan from an arraylist of tables.
	 * 
	 * @param ArrayList
	 *                  <Table>
	 *                  tables
	 */
	public void generateFloorPlan(ArrayList<Table> tables) {
		int tableSize = tables.get(0).getSize();

		if (!sizeSet) {
			this.MAX_RIGHT = (int) ((Math.ceil(Math.sqrt(tables.size()))) * ((tableSize / 2) + 2) * SCALE_FACTOR + 200);
			// System.out.println(MAX_RIGHT);
			this.MAX_BOTTOM = this.MAX_RIGHT;
		}

		double determinedX = 0;
		double determinedY = 0;

		for (int i = 0; i < tables.size(); i++) {
			DispTable tableCreation = new DispTable();

			tableCreation.setReal(true);
			tableCreation.setOriginalTable(tables.get(i));

			tableCreation.setHeight(2 * SCALE_FACTOR);
			tableCreation.setWidth(tableSize * SCALE_FACTOR / 2);

			if (i == 0) {
				determinedX = SCALE_FACTOR * 10;
				determinedY = 100;
			} else {
				determinedX = tableShapes.get(i - 1).getX() + tableSize * SCALE_FACTOR / 2 + SCALE_FACTOR * 2;
				determinedY = tableShapes.get(i - 1).getY();
				// System.out.println(determinedX);
				if (determinedX >= (this.MAX_RIGHT - tableSize * SCALE_FACTOR / 2)) {
					determinedX = SCALE_FACTOR * 10;
					determinedY = determinedY + SCALE_FACTOR * 6;
				}
			}

			tableCreation.setX(determinedX);
			tableCreation.setY(determinedY);

			tableShapes.add(tableCreation);

			for (int j = 0; j < tables.get(i).getStudents().size(); j++) {
				DispStudent studentCreation = new DispStudent();

				studentCreation.setReal(true);

				studentCreation.setRadius(SCALE_FACTOR - 2);

				if (j < (tableSize / 2)) {
					studentCreation.setX(determinedX + SCALE_FACTOR * j);
					studentCreation.setY(determinedY - SCALE_FACTOR - OFFSET_FACTOR);
				} else {
					studentCreation.setX(determinedX + SCALE_FACTOR * (j - (tableSize / 2)));
					studentCreation.setY(determinedY + SCALE_FACTOR * 2 + OFFSET_FACTOR);
				}

				studentCreation.setOriginalStudent(tables.get(i).getStudents().get(j));
				studentShapes.add(studentCreation);
			}
		}

		boolean boardExhausted = false;

		while (!boardExhausted) {

			DispTable tableCreation = new DispTable();

			tableCreation.setReal(false);

			tableCreation.setHeight(2 * SCALE_FACTOR);
			tableCreation.setWidth(tableSize * SCALE_FACTOR / 2);

			determinedX = tableShapes.get(tableShapes.size() - 1).getX() + tableSize * SCALE_FACTOR / 2
					+ SCALE_FACTOR * 2;
			determinedY = tableShapes.get(tableShapes.size() - 1).getY();
			if (determinedX >= MAX_RIGHT - tableSize * SCALE_FACTOR / 2) {
				determinedX = SCALE_FACTOR * 10;
				determinedY = determinedY + SCALE_FACTOR * 6;
			}

			tableCreation.setX(determinedX);
			tableCreation.setY(determinedY);

			// System.out.println(determinedY);

			if (determinedY > (MAX_BOTTOM - SCALE_FACTOR * 10)) {
				boardExhausted = true;
			} else {
				tableShapes.add(tableCreation);
			}
		}
	}

	/**
	 * This method will generate a floor plan from an arraylist of tables. Config options are:
	 * 	1."ROUND TABLES" : generates the tables as circles
	 * 
	 * @param ArrayList
	 *                  <Table>
	 *                  tables
	 * @param String    config
	 */
	public void generateFloorPlan(ArrayList<Table> tables, String config) {
		if (config == "ROUND TABLES") {
			int tableSize = tables.get(0).getSize();
			int distToNextTable = tableSize * SCALE_FACTOR / 2 + SCALE_FACTOR * 2;

			if (!sizeSet) {
				this.MAX_RIGHT = (int) ((Math.ceil(Math.sqrt(tables.size()))) * ((tableSize / 2) + 2) * SCALE_FACTOR
						+ 200);
				// System.out.println(MAX_RIGHT);
				this.MAX_BOTTOM = this.MAX_RIGHT;
			}

			double determinedX = 0;
			double determinedY = 0;

			boolean offset = false;

			for (int i = 0; i < tables.size(); i++) {
				DispTable tableCreation = new DispTable();

				tableCreation.setReal(true);
				tableCreation.setRound(true);
				tableCreation.setOriginalTable(tables.get(i));

				tableCreation.setHeight(tableSize * SCALE_FACTOR / 3);
				tableCreation.setWidth(tableSize * SCALE_FACTOR / 3);

				if (i == 0) {
					determinedX = SCALE_FACTOR * 6;
					determinedY = 100;
				} else {
					determinedX = tableShapes.get(i - 1).getX() + distToNextTable;
					determinedY = tableShapes.get(i - 1).getY();

					// System.out.println(determinedX);

					if (determinedX >= (this.MAX_RIGHT - 2 * SCALE_FACTOR - tableSize * SCALE_FACTOR / 3)) {
						determinedX = SCALE_FACTOR * 6 + distToNextTable * (Math.cos(60 * Math.PI / 180));
						determinedY = determinedY + distToNextTable * (Math.sin(60 * Math.PI / 180));

						if (offset) {
							determinedX = SCALE_FACTOR * 6;
							offset = false;
						} else {
							offset = true;
						}

					}

				}

				tableCreation.setX(determinedX);
				tableCreation.setY(determinedY);

				tableShapes.add(tableCreation);

				double tableCenterX = determinedX + tableCreation.getHeight() / 2;
				double tableCenterY = determinedY + tableCreation.getHeight() / 2;

				for (int j = 0; j < tables.get(i).getStudents().size(); j++) {
					DispStudent studentCreation = new DispStudent();

					studentCreation.setReal(true);

					studentCreation.setRadius(SCALE_FACTOR - 2);

					double currentAngle = j * 2 * Math.PI / tableSize;
					// System.out.println(currentAngle);

					studentCreation.setX(tableCenterX + tableCreation.getHeight() * Math.cos(currentAngle) / 1.4
							- studentCreation.getRadius() / 2);
					studentCreation.setY(tableCenterY + tableCreation.getHeight() * Math.sin(currentAngle) / 1.4
							- studentCreation.getRadius() / 2);

					studentCreation.setOriginalStudent(tables.get(i).getStudents().get(j));
					studentShapes.add(studentCreation);
				}
			}

			boolean boardExhausted = false;

			while (!boardExhausted) {

				DispTable tableCreation = new DispTable();

				tableCreation.setReal(false);
				tableCreation.setRound(true);

				tableCreation.setHeight(tableSize * SCALE_FACTOR / 3);
				tableCreation.setWidth(tableSize * SCALE_FACTOR / 3);

				determinedX = tableShapes.get(tableShapes.size() - 1).getX() + distToNextTable;
				determinedY = tableShapes.get(tableShapes.size() - 1).getY();
				if (determinedX >= MAX_RIGHT - SCALE_FACTOR * 2 - tableSize * SCALE_FACTOR / 3) {
					determinedX = SCALE_FACTOR * 6 + distToNextTable * (Math.cos(60 * Math.PI / 180));
					determinedY = determinedY + distToNextTable * (Math.sin(60 * Math.PI / 180));

					if (offset) {
						determinedX = SCALE_FACTOR * 6;
						offset = false;
					} else {
						offset = true;
					}
				}

				tableCreation.setX(determinedX);
				tableCreation.setY(determinedY);

				// System.out.println(determinedY);

				if (determinedY > (MAX_BOTTOM - SCALE_FACTOR * 2 - SCALE_FACTOR * tableSize / 3)) {
					boardExhausted = true;
				} else {
					tableShapes.add(tableCreation);
				}

			}
		}
	}

	/**
	 * This method will regenerate a floor plan.
	 * 
	 * @param String config
	 */
	private void regenerateFloorPlan(String config) {
		if (config == "round") {
			ArrayList<Table> paramTables = new ArrayList<Table>(0);

			for (int i = 0; i < tableShapes.size(); i++) {

				// System.out.println(tableShapes.get(i).isReal());

				if (tableShapes.get(i).isReal()) {
					paramTables.add(tableShapes.get(i).getOriginalTable());
				}
			}

			int tableSize = tableShapes.get(0).getOriginalTable().getSize();

			this.tableShapes.clear();
			this.studentShapes.clear();

			this.generateFloorPlan(paramTables, "ROUND TABLES");

			boolean messageShow = false;

			for (int i = 0; i < tableShapes.size(); i++) {
				if (tableShapes.get(i).isReal()) {
					if (tableShapes.get(i).getY() > MAX_BOTTOM - tableSize * SCALE_FACTOR / 3 - SCALE_FACTOR * 2) {
						messageShow = true;
						MAX_BOTTOM = (int) (tableShapes.get(i).getY() + tableSize * SCALE_FACTOR / 3
								+ SCALE_FACTOR * 2);
					}
				} else {
					// System.out.println("false");
				}
			}

			if (messageShow) {
				JOptionPane.showMessageDialog(null, "your height was modified to fit all tables");
			}
		}
	}

	/**
	 * SidePanel: This class will display buttons to the left.
	 * 
	 * @author Artem
	 * @author Anthony
	 */
	private class SidePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		final DispRectangle saveButton = new DispRectangle(10, 10, 100, 40);
		final DispRectangle saveAsButton = new DispRectangle(10, 60, 100, 40);
		final DispRectangle loadButton = new DispRectangle(10, 110, 100, 40);
		final DispRectangle backButton = new DispRectangle(10, 160, 100, 40);
		final DispRectangle switchButton = new DispRectangle(10, 210, 100, 40);
		final DispRectangle resizeButton = new DispRectangle(10, 260, 100, 40);

		private boolean saveButtonState;
		private boolean saveAsButtonState;
		private boolean loadButtonState;
		private boolean backButtonState;
		private boolean switchButtonState;
		private boolean resizeButtonState;

		private boolean clickPending;

		private MyMouseListener mouseListener2;

		/**
		 * Class constructor.
		 */
		public SidePanel() {
			this.mouseListener2 = new MyMouseListener();
			this.addMouseListener(this.mouseListener2);
			this.addMouseMotionListener(this.mouseListener2);
			this.addMouseWheelListener(this.mouseListener2);

			this.handleAll();
		}

		/**
		 * This method will return true if a click is pending.
		 * 
		 * @return boolean clickPending
		 */
		public boolean anyPending() {
			return this.clickPending;
		}

		/**
		 * This method will return true if the save button is pending.
		 * 
		 * @return boolean saveButtonState
		 */
		public boolean saveButtonPending() {
			return this.saveButtonState;
		}

		/**
		 * This method will return true if the save as button is pending.
		 * 
		 * @return boolean saveAsButtonState
		 */
		public boolean saveAsButtonPending() {
			return this.saveAsButtonState;
		}

		/**
		 * This method will return true if the load button is pending.
		 * 
		 * @return boolean loadButtonState
		 */
		public boolean loadButtonPending() {
			return this.loadButtonState;
		}

		/**
		 * This method will return true if the back button is pending.
		 * 
		 * @return boolean backButtonState
		 */
		public boolean backButtonPending() {
			return this.backButtonState;
		}

		/**
		 * This method will return true if the switch button is pending.
		 * 
		 * @return boolean switchButtonState
		 */
		public boolean switchButtonPending() {
			return this.switchButtonState;
		}

		/**
		 * This method will return true if the resize button is pending.
		 * 
		 * @return boolean resizeButtonState
		 */
		public boolean resizeButtonPending() {
			return this.resizeButtonState;
		}

		/**
		 * This method will handle all click pending states.
		 */
		public void handleAll() {
			this.clickPending = false;

			this.saveButtonState = false;
			this.saveAsButtonState = false;
			this.loadButtonState = false;
			this.backButtonState = false;
			this.switchButtonState = false;
			this.resizeButtonState = false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setDoubleBuffered(true);

			saveButton.draw(g, Color.CYAN);
			saveAsButton.draw(g, Color.CYAN);
			loadButton.draw(g, Color.CYAN);

			g.setColor(Color.BLACK);
			g.drawString("SAVE", (int) saveButton.getX() + OFFSET_FACTOR, (int) saveButton.getY() + OFFSET_FACTOR * 3);
			g.drawString("SAVE AS", (int) saveAsButton.getX() + OFFSET_FACTOR,
					(int) saveAsButton.getY() + OFFSET_FACTOR * 3);
			g.drawString("LOAD", (int) loadButton.getX() + OFFSET_FACTOR, (int) loadButton.getY() + OFFSET_FACTOR * 3);

			if (disp.getUIState() != UIState.STATE_VIEWING) {
				backButton.draw(g, Color.YELLOW);
				g.setColor(Color.BLACK);
				g.drawString("BACK", (int) backButton.getX() + OFFSET_FACTOR,
						(int) backButton.getY() + OFFSET_FACTOR * 3);
			} else {
				resizeButton.draw(g, Color.RED);
				g.setColor(Color.BLACK);
				g.drawString("RESIZE ROOM", (int) resizeButton.getX() + OFFSET_FACTOR,
						(int) resizeButton.getY() + OFFSET_FACTOR * 3);
			}

			if ((disp.getUIState() == UIState.STATE_STUDENT_SELECTED)
					|| (disp.getUIState() == UIState.STATE_TABLE_SELECTED)) {
				switchButton.draw(g, Color.GREEN);
				g.setColor(Color.BLACK);
				g.drawString("SWITCH WITH", (int) switchButton.getX() + OFFSET_FACTOR,
						(int) switchButton.getY() + OFFSET_FACTOR * 3);
			}

			g.setColor(Color.WHITE);
			g.fillRect(5, 400 - OFFSET_FACTOR * 3, 190, 2000);

			g.setColor(Color.BLACK);

			if (focusedStudent.isHovered()) {
				Student dataStudent = focusedStudent.getOriginalStudent();

				g.drawString("Student Name: " + dataStudent.getName(), 10, 400);

			} else if (focusedTable.isHovered()) {
				if (focusedTable.isReal()) {
					Table dataTable = focusedTable.getOriginalTable();

					g.drawString("Table size: " + Integer.toString(dataTable.getSize()), 10, 400);
				}
			}

			if (mouseListener2.clickPending()) {
				Point clickPos = mouseListener2.getClick();

				this.clickPending = true;

				mouseListener2.clickHandled();

				if (backButton.getBoundingBox().contains(clickPos)) {
					this.backButtonState = true;
				} else if (switchButton.getBoundingBox().contains(clickPos)) {
					this.switchButtonState = true;
				} else if (saveButton.getBoundingBox().contains(clickPos)) {
					this.saveButtonState = true;
				} else if (saveAsButton.getBoundingBox().contains(clickPos)) {
					this.saveAsButtonState = true;
				} else if (loadButton.getBoundingBox().contains(clickPos)) {
					this.loadButtonState = true;
				} else if (resizeButton.getBoundingBox().contains(clickPos)) {
					this.resizeButtonState = true;
				}
			}
		}
	}

	/**
	 * Display: This class will display the floor plan to a panel.
	 * 
	 * @author Artem
	 * @author Anthony
	 */
	private class Display extends JPanel {
		private static final long serialVersionUID = 1L;

		private UIState state;
		private MyMouseListener mouseListener;
		private int camX = 0;
		private int camY = 0;
		private int dx = 0;
		private int dy = 0;

		/**
		 * Class constructor.
		 */
		public Display() {
			this.mouseListener = new MyMouseListener();
			this.addMouseListener(this.mouseListener);
			this.addMouseMotionListener(this.mouseListener);
			this.addMouseWheelListener(this.mouseListener);
			this.setBackground(LIGHT_GRAY);

			this.state = UIState.STATE_VIEWING;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setDoubleBuffered(true);

			updateCamera(g);

			// Draws borders
			g.fillRect(MAX_LEFT + 5, MAX_TOP + 5, MAX_RIGHT - 10, 5);
			g.fillRect(MAX_LEFT + 5, MAX_TOP + 5, 5, MAX_BOTTOM - 10);
			g.fillRect(MAX_LEFT + 10, MAX_BOTTOM - 10, MAX_RIGHT - 20, 5);
			g.fillRect(MAX_RIGHT - 10, MAX_TOP + 5, 5, MAX_BOTTOM - 10);

			//

			for (int i = 0; i < studentShapes.size(); i++) {
				studentShapes.get(i).drawObject(g);
			}

			for (int i = 0; i < tableShapes.size(); i++) {
				((DispTable) tableShapes.get(i)).drawObject(g);
			}

			Point mousePos = this.mouseListener.getPos();
			applyMouseTransformation(mousePos);

			boolean studentHovered = false;

			// Table hover is set and text box displayed directly
			if (mouseListener.isDragging() == false) {
				for (int i = 0; i < tableShapes.size(); i++) {
					if (tableShapes.get(i).getBoundingBox().contains(mousePos)) {

						tableShapes.get(i).setHovered(true);
						((DispTable) tableShapes.get(i)).drawBox(g, i);

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

				if ((mouseListener.clickPending()) || (sidePnl.anyPending())) {
					Point clickPos = mouseListener.getClick();
					applyMouseTransformation(clickPos);
					mouseListener.clickHandled();

					if (sidePnl.anyPending()) {
						clickPos = new Point(0, 0);
					}

					// click save or load button
					if (sidePnl.saveButtonPending()) {
						saveFloorPlan();
						sidePnl.handleAll();
						JOptionPane.showMessageDialog(null, infoLabel, "Layout saved",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (sidePnl.saveAsButtonPending()) {
						saveAsFloorPlan();
						sidePnl.handleAll();

					} else if (sidePnl.loadButtonPending()) {
						loadFloorPlan(false);
						sidePnl.handleAll();
					}

					if (sidePnl.resizeButtonPending()) {
						if (this.state == UIState.STATE_VIEWING) {
							this.state = UIState.STATE_RESIZING;
						}
					}

					if (this.state == UIState.STATE_RESIZING) {
						if (sidePnl.backButtonPending()) {
							this.state = UIState.STATE_VIEWING;
						}
					}

					if (this.state == UIState.STATE_VIEWING) {
						for (int i = 0; i < studentShapes.size(); i++) {
							if (studentShapes.get(i).getBoundingBox().contains(clickPos)) {

								this.state = UIState.STATE_STUDENT_SELECTED;

								studentShapes.get(i).setSelected(true);
								selectedStudent = studentShapes.get(i);

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

					} else if (this.state == UIState.STATE_STUDENT_SELECTED
							|| this.state == UIState.STATE_TABLE_SELECTED) {

						// System.out.println(Boolean.toString(sidePnl.backButtonPending()));

						if (sidePnl.backButtonPending()) {

							this.state = UIState.STATE_VIEWING;

							for (int i = 0; i < studentShapes.size(); i++) {
								studentShapes.get(i).setSelected(false);
							}

							for (int i = 0; i < tableShapes.size(); i++) {
								tableShapes.get(i).setSelected(false);
							}

						} else if (sidePnl.switchButtonPending()) {

							switch (this.state) {
							case STATE_STUDENT_SELECTED:

								this.state = UIState.STATE_STUDENT_MOVING;

								DispRectangle tableSearch = new DispRectangle(selectedStudent.getX() - 10,
										selectedStudent.getY() - 10, selectedStudent.getRadius() + 20,
										selectedStudent.getRadius() + 20);

								for (int i = 0; i < tableShapes.size(); i++) {
									DispRectangle currTable = tableShapes.get(i);

									if (tableSearch.getBoundingBox().intersects(currTable.getBoundingBox())) {
										i = tableShapes.size();

										DispRectangle studentSearch = new DispRectangle(currTable.getX() - 10,
												currTable.getY() - 10, currTable.getWidth() + 20,
												currTable.getHeight() + 20);

										for (int j = 0; j < studentShapes.size(); j++) {
											if (studentSearch.getBoundingBox()
													.intersects(studentShapes.get(j).getBoundingBox())) {

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

					} else if (this.state == UIState.STATE_TABLE_MOVING) {
						for (int i = 0; i < tableShapes.size(); i++) {
							if (tableShapes.get(i).getBoundingBox().contains(clickPos)) {
								if (tableShapes.get(i).isHighlighted()) {
									DispRectangle currTable = selectedTable;
									DispRectangle futureTable = tableShapes.get(i);

									double dx = futureTable.getX() - currTable.getX();
									double dy = futureTable.getY() - currTable.getY();

									DispRectangle studentSearch = new DispRectangle(currTable.getX() - 10,
											currTable.getY() - 10, currTable.getWidth() + 20,
											currTable.getHeight() + 20);

									for (int j = 0; j < studentShapes.size(); j++) {
										if (studentSearch.getBoundingBox()
												.intersects(studentShapes.get(j).getBoundingBox())) {

											studentShapes.get(j).setHighlighted(true);
											studentShapes.get(j).setX(studentShapes.get(j).getX() + dx);
											studentShapes.get(j).setY(studentShapes.get(j).getY() + dy);

										}
									}

									DispRectangle studentSearch2 = new DispRectangle(futureTable.getX() - 10,
											futureTable.getY() - 10, futureTable.getWidth() + 20,
											futureTable.getHeight() + 20);

									for (int j = 0; j < studentShapes.size(); j++) {
										if (studentSearch2.getBoundingBox()
												.intersects(studentShapes.get(j).getBoundingBox())) {
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

									tableShapes.get(selectedTableIdx)
									.setX(tableShapes.get(selectedTableIdx).getX() + dx);
									tableShapes.get(selectedTableIdx)
									.setY(tableShapes.get(selectedTableIdx).getY() + dy);

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

		/**
		 * This method will apply the translate and scale effects to the mouse position.
		 * 
		 * @param Point pos
		 */
		public void applyMouseTransformation(Point pos) {
			int width = this.getSize().width;
			int height = this.getSize().height;
			double zoomScale = mouseListener.getZoomScale();
			pos.x = (int) ((((pos.x - (width / 2)) / zoomScale)) + (width / 2) + camX);
			pos.y = (int) ((((pos.y - (height / 2)) / zoomScale)) + (height / 2) + camY);
		}

		/**
		 * This method will update the camera by zooming and panning.
		 * 
		 * @param Graphics g
		 */
		public void updateCamera(Graphics g) {
			zooming(g);
			panning(g);
		}

		/**
		 * This method will return the UIState.
		 * 
		 * @return UIState
		 */
		public UIState getUIState() {
			return this.state;
		}

		/**
		 * This method will zoom the camera.
		 * 
		 * @param Graphics g
		 */
		public void zooming(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			int width = this.getSize().width;
			int height = this.getSize().height;
			g2.translate(width / 2, height / 2);
			g2.scale(mouseListener.getZoomScale(), mouseListener.getZoomScale());
			g2.translate(-width / 2, -height / 2);
		}

		/**
		 * This method will pan the camera.
		 * 
		 * @param Graphics g
		 */
		public void panning(Graphics g) {
			if (mouseListener.isDragging()) {
				dx = (int) (mouseListener.getReleaseX() - mouseListener.getClick().getX());
				dy = (int) (mouseListener.getReleaseY() - mouseListener.getClick().getY());
				int totalX = camX + dx;
				int totalY = camY + dy;
				g.translate(-totalX, -totalY);
			} else {
				camX += dx;
				camY += dy;
				dx = 0;
				dy = 0;
				g.translate(-camX, -camY);
			}
		}
	}
}
