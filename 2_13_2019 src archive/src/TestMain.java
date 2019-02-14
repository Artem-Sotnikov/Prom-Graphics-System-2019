import javax.swing.JFrame;
import java.util.ArrayList;

public class TestMain { 
 static JFrame window; 

 
 public static void main(String[] args) {
  window = new FloorPlan();
  
  ArrayList<Table> testTables = new ArrayList<Table>();
  
  for (int i = 0; i < 40; i++) {
   testTables.add(new Table());
  }
  
  ((FloorPlan) window).generateFloorPlan(testTables);

  while (true) {
   ((FloorPlan) window).displayFloorPlan();
  }
 } 
}
