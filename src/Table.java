// Contains testing code by Carol Chen

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
	private static final long serialVersionUID = 1L;

	private int size;
	private ArrayList<Student> students;

	public Table(int initSize) { 
		size = initSize;
	}

	public Table() {
		ArrayList<Student> rList = new ArrayList<Student>(0);

		rList.add(new Student("Bob"));
		rList.add(new Student("Joe"));
		rList.add(new Student("Bart"));
		rList.add(new Student("Alice"));
		rList.add(new Student("Carly"));
		rList.add(new Student("Denis"));
		rList.add(new Student("Eve"));
		rList.add(new Student("Fanny"));
		rList.add(new Student("Nate"));
		rList.add(new Student("Lloyd"));
		rList.add(new Student("Ken"));
		
		this.students = rList;
		this.size = 12;
	}

	public ArrayList<Student> getStudents() { 
		return students; 
	}

	public void setStudents(ArrayList<Student> newStudents) { 
		students = newStudents;
	}

	public int getSize() {		
		return this.size;
	}
}
