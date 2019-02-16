import java.io.Serializable;

public class DispStudent extends DispCircle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Student originalStudent;
	
	public DispStudent() {}
	
	public DispStudent(double x, double y, double radius, Student originalStudent) {
		super(x, y, radius);
		this.originalStudent = originalStudent;
	}

	public Student getOriginalStudent() {
		return originalStudent;
	}

	public void setOriginalStudent(Student originalStudent) {
		this.originalStudent = originalStudent;
	}
	
	@Override
	public String toString() {
		return "X: " + this.getX() + "\nY: " + this.getY() + "\nRadius: " + this.getRadius() + "\nName: " + this.originalStudent.getName();
	}
}