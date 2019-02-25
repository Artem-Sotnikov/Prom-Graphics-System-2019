import java.io.Serializable;
import java.util.ArrayList;

/**
 * SaveFile: This class will store save information.
 * 
 * @author Artem
 * @author Anthony
 */
public class SaveFile implements Serializable {
	private static final long serialVersionUID = 1L;

	private int MAX_BOTTOM = 2000;
	private int MAX_RIGHT = 2000;
	private ArrayList<DispTable> tableList;
	private ArrayList<DispStudent> studentList;

	/**
	 * Class constructor with parameters.
	 * 
	 * @param MAX_BOTTOM the max bottom
	 * @param MAX_RIGHT the max right
	 * @param tableList the table list
	 * @param studentList the student list
	 */
	public SaveFile(int MAX_BOTTOM, int MAX_RIGHT, ArrayList<DispTable> tableList, ArrayList<DispStudent> studentList) {
		this.MAX_BOTTOM = MAX_BOTTOM;
		this.MAX_RIGHT = MAX_RIGHT;
		this.tableList = tableList;
		this.studentList = studentList;
	}

	/**
	 * This method will reset highlighted states.
	 */
	public void resetStates() {
		for (DispTable table : tableList) {
			table.setHovered(false);
			table.setHighlighted(false);
			table.setSelected(false);
		}

		for (DispStudent student : studentList) {
			student.setHovered(false);
			student.setHighlighted(false);
			student.setSelected(false);
		}
	}

	/**
	 * @return the mAX_BOTTOM
	 */
	public int getMAX_BOTTOM() {
		return MAX_BOTTOM;
	}

	/**
	 * @param mAX_BOTTOM the mAX_BOTTOM to set
	 */
	public void setMAX_BOTTOM(int mAX_BOTTOM) {
		MAX_BOTTOM = mAX_BOTTOM;
	}

	/**
	 * @return the mAX_RIGHT
	 */
	public int getMAX_RIGHT() {
		return MAX_RIGHT;
	}

	/**
	 * @param mAX_RIGHT the mAX_RIGHT to set
	 */
	public void setMAX_RIGHT(int mAX_RIGHT) {
		MAX_RIGHT = mAX_RIGHT;
	}

	/**
	 * @return the tableList
	 */
	public ArrayList<DispTable> getTableList() {
		return tableList;
	}

	/**
	 * @return the studentList
	 */
	public ArrayList<DispStudent> getStudentList() {
		return studentList;
	}

	/**
	 * @param tableList the tableList to set
	 */
	public void setTableList(ArrayList<DispTable> tableList) {
		this.tableList = tableList;
	}

	/**
	 * @param studentList the studentList to set
	 */
	public void setStudentList(ArrayList<DispStudent> studentList) {
		this.studentList = studentList;
	}
}