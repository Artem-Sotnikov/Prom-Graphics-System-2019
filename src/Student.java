import java.io.Serializable;
import java.util.ArrayList;

/**
 * Student: This class will handle student information.
 * 
 * @author Carol
 */
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String studentNumber;
	private ArrayList<String> dietaryRestrictions;
	private ArrayList<String> friendStudentNumbers;

	/**
	 * Class constructor.
	 * 
	 * @param name the name
	 */
	public Student(String name) {
		this.name = name;
	}

	/**
	 * Class constructor with parameters.
	 * 
	 * @param initName the initial name
	 * @param initStudentNumber the initial student number
	 * @param initDietaryRestrictions the initial dietary restrictions
	 * @param initFriendStudentNumbers the initial friend student numbers
	 */
	public Student(String initName, String initStudentNumber, ArrayList<String> initDietaryRestrictions,
			ArrayList<String> initFriendStudentNumbers) {
		name = initName;
		studentNumber = initStudentNumber;
		dietaryRestrictions = initDietaryRestrictions;
		friendStudentNumbers = initFriendStudentNumbers;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the studentNumber
	 */
	public String getStudentNumber() {
		return studentNumber;
	}

	/**
	 * @param studentNumber the studentNumber to set
	 */
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	/**
	 * @return the dietaryRestrictions
	 */
	public ArrayList<String> getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	/**
	 * @param dietaryRestrictions the dietaryRestrictions to set
	 */
	public void setDietaryRestrictions(ArrayList<String> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}

	/**
	 * @return the friendStudentNumbers
	 */
	public ArrayList<String> getFriendStudentNumbers() {
		return friendStudentNumbers;
	}

	/**
	 * @param friendStudentNumbers the friendStudentNumbers to set
	 */
	public void setFriendStudentNumbers(ArrayList<String> friendStudentNumbers) {
		this.friendStudentNumbers = friendStudentNumbers;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
