// Contains testing code by Carol Chen

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable { 
	private static final long serialVersionUID = 1L;
	private String name;
	private String studentNumber; 
	private ArrayList<String> dietaryRestrictions; 
	private ArrayList<String> friendStudentNumbers; 

	Student(String initName, 
			String initStudentNumber, 
			ArrayList<String> initDietaryRestrictions, 
			ArrayList<String> initFriendStudentNumbers) { 
		name = initName; 
		studentNumber = initStudentNumber; 
		dietaryRestrictions = initDietaryRestrictions;
		friendStudentNumbers = initFriendStudentNumbers;
	}

	Student (String name) {
		this.name = name;
	}

	public String getName() { 
		return name;
	}

	public void setName(String newName) { 
		name = newName;
	}

	public String getStudentNumber() { 
		return studentNumber; 
	}

	public void studentNumber(String newStudentNumber) { 
		studentNumber = newStudentNumber; 
	}

	public ArrayList<String> getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	public void setDietaryRestrictions(ArrayList<String> newDietaryRestrictions) {
		dietaryRestrictions = newDietaryRestrictions;
	}

	public ArrayList<String> getFriendStudentNumbers() {
		return friendStudentNumbers;
	}

	public void setFriendStudentNumbers(ArrayList<String> newFriendStudentNumbers) {
		friendStudentNumbers = newFriendStudentNumbers;
	}
}
