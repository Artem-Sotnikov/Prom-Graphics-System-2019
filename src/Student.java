import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public Student(String n) {
		this.name = n;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

