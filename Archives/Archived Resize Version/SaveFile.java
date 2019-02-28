import java.io.Serializable;
import java.util.ArrayList;

public class SaveFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<DispTable> tableList;
	private ArrayList<DispStudent> studentList;
	
	public SaveFile(ArrayList<DispTable> tableList, ArrayList<DispStudent> studentList) {
		this.tableList = tableList;
		this.studentList = studentList;
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