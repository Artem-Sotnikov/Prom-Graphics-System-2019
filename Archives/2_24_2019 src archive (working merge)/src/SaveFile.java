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

	private ArrayList<DispTable> tableList;
	private ArrayList<DispStudent> studentList;

	/**
	 * Class constructor with parameters.
	 * 
	 * @param tableList
	 * @param studentList
	 */
	public SaveFile(ArrayList<DispTable> tableList, ArrayList<DispStudent> studentList) {
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