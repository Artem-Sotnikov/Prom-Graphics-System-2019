import java.util.ArrayList;

public class TestMain {
 public static void main(String args[]) throws Exception{
  FloorPlan aPlan = new FloorPlan();
  
  ArrayList<Table> thing = new ArrayList<Table>();
  thing.add(new Table(10));
  
  
  aPlan.generateFloorPlan(thing);
  aPlan.displayFloorPlan();
}
}

