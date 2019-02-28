import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LoadFile {
	private ArrayList<DispRectangle> tableList;
	private ArrayList<DispStudent> studentList;
	
	public LoadFile() {
		loadShapes();
	}
	
	public LoadFile(ArrayList<DispRectangle> tableL, ArrayList<DispStudent> studentL) {
		this.studentList = studentL;
		this.tableList = tableL;
	}
	
	public void loadShapes() {
		loadTables();
		loadStudents();
	}
	
	public void saveShapes() {
		saveTables();
		saveStudents();
	}
	
	public void loadTables() {
		boolean cont = true;
		FileInputStream f;
		ObjectInputStream o;
		ArrayList<DispRectangle> loadList = new ArrayList<DispRectangle>();
		
		try {
			f = new FileInputStream(new File("src/savefiles/savetables.txt"));
			o = new ObjectInputStream(f);
			
			while (cont) {
				DispRectangle obj = (DispRectangle) o.readObject();
				if (obj != null) {
					loadList.add(obj);
				} else {
					cont = false;
				}
			}
			o.close();
			f.close();
		} catch (EOFException e) {
			this.tableList = loadList;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadStudents() {
		boolean cont = true;
		FileInputStream f;
		ObjectInputStream o;
		ArrayList<DispStudent> loadList = new ArrayList<DispStudent>();
		
		try {
			f = new FileInputStream(new File("src/savefiles/savestudents.txt"));
			o = new ObjectInputStream(f);
			
			while (cont) {
				DispStudent obj = (DispStudent) o.readObject();
				if (obj != null) {
					loadList.add(obj);
				} else {
					cont = false;
				}
			}
			o.close();
			f.close();
		} catch (EOFException e) {
			this.studentList = loadList;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveTables() {
		try {
			FileOutputStream f = new FileOutputStream(new File("src/savefiles/savetables.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			for (DispRectangle obj : this.tableList) {
				o.writeObject(obj);
			}
			o.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveStudents() {
		try {
			FileOutputStream f = new FileOutputStream(new File("src/savefiles/savestudents.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			for (DispStudent obj : this.studentList) {
				o.writeObject(obj);
			}
			o.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void outputTables() {
		for (DispRectangle table : this.tableList) {
			System.out.println(table.toString());
			System.out.println("");
		}
	}
	
	public void outputStudents() {
		for (DispStudent student : this.studentList) {
			System.out.println(student.toString());
			System.out.println("");
		}
	}
	
	/**
	 * @return the tableList
	 */
	public ArrayList<DispRectangle> getTableList() {
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
	public void setTableList(ArrayList<DispRectangle> tableList) {
		this.tableList = tableList;
	}

	/**
	 * @param studentList the studentList to set
	 */
	public void setStudentList(ArrayList<DispStudent> studentList) {
		this.studentList = studentList;
	}

	public static void main(String[] args) {
		ArrayList<DispRectangle> tableList = new ArrayList<DispRectangle>();
		tableList.add(new DispRectangle(500, 500, 100, 50));
		
		ArrayList<DispStudent> studentList = new ArrayList<DispStudent>();
		studentList.add(new DispStudent(300, 70, 18, new Student("Joe")));
		studentList.add(new DispStudent(300, 90, 18, new Student("Karen")));
		studentList.add(new DispStudent(500, 90, 18, new Student("Fred")));
		
		LoadFile loadFile = new LoadFile(tableList, studentList);
		
		loadFile.saveShapes();
		loadFile.outputTables();
		loadFile.outputStudents();
	}
}