import java.util.ArrayList;

public class Table {
	public ArrayList<Student> getStudents() {
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
		//rList.add(new Student("Sarah"));
		
		return rList;
	}
	
	public int getSize() {		
		return 12;
	}
}
