import java.util.ArrayList;

/**
 * SeatingAlg: This class will handle seating algorithms.
 * 
 * @author Carol
 */
public class SeatingAlg {
	/**
	 * This method will generate tables.
	 * 
	 * @param students
	 * @param maxStudentsPerTable
	 * @return
	 */
	public ArrayList<Table> generateTables(ArrayList<Student> students, int maxStudentsPerTable) {
		int numStudents = students.size();
		ArrayList<Table> res = new ArrayList<Table>();
		for (int i = 0; i < numStudents / maxStudentsPerTable; i++) {
			Table currTable = new Table(maxStudentsPerTable);
			currTable.setStudents(
					new ArrayList<>(students.subList(i * maxStudentsPerTable, (i + 1) * maxStudentsPerTable)));
			res.add(currTable);
		}
		if (numStudents % maxStudentsPerTable > 0) {
			Table lastTable = new Table(numStudents % maxStudentsPerTable);
			lastTable.setStudents(
					new ArrayList<>(students.subList(numStudents - numStudents % maxStudentsPerTable, numStudents)));
			res.add(lastTable);
		}

		return res;
	}
}
