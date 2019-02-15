import javax.swing.JFrame;
import java.util.ArrayList;

public class TestMain { 
	static JFrame window; 

	public static void main(String[] args) {
		window = new FloorPlan();

		ArrayList<Table> testTables = new ArrayList<Table>();

		for (int i = 0; i < 20; i++) {
			testTables.add(new Table());
		}
		
		// either load from existing file OR generate from list of tables
		//((FloorPlan) window).loadShapesFromFile();
		((FloorPlan) window).generateFloorPlan(testTables);
		((FloorPlan) window).displayFloorPlan();
		
	} 
}