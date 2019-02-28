import java.io.Serializable;
import java.util.ArrayList;

/**
 * Table: This class will handle information related to tables.
 * 
 * @author Carol
 */
public class Table implements Serializable {
	private static final long serialVersionUID = 1L;

	private int size;
	private ArrayList<Student> students;

	/**
	 * Class constructor with parameter.
	 * 
	 * @param initSize the initial size
	 */
	public Table(int initSize) {
		size = initSize;
	}

	/**
	 * Class constructor.
	 */
	public Table() {
		ArrayList<Student> rList = new ArrayList<Student>(0);

		rList.add(new Student("Bob"));
//		rList.add(new Student("Joe"));
//		rList.add(new Student("Bart"));
//		rList.add(new Student("Alice"));
//		rList.add(new Student("Carly"));
//		rList.add(new Student("Denis"));
//		rList.add(new Student("Eve"));
//		rList.add(new Student("Fanny"));
//		rList.add(new Student("Nate"));
//		rList.add(new Student("Lloyd"));
//		rList.add(new Student("Ken"));

		this.students = rList;
		this.size = 1;
	}

	/**
	 * @return students the students
	 */
	public ArrayList<Student> getStudents() {
		return students;
	}

	/**
	 * @param newStudents the arraylist of students
	 */
	public void setStudents(ArrayList<Student> newStudents) {
		students = newStudents;
	}

	/**
	 * @return int size
	 */
	public int getSize() {
		return this.size;
	}
}
