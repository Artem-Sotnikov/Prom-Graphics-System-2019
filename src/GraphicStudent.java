import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class GraphicStudent extends Shape {
  private double radius;
  private Student originalStudent;
  

  public GraphicStudent(double x, double y, double rad) {
   super(x, y);
   this.radius = rad;
   // TODO Auto-generated constructor stub
  }
  
  public GraphicStudent() {};
  
  public double getRadius() {
   return this.radius;
  }  
  
  public void setRadius(double rad) {
   this.radius = rad;
  }
  
  public Student getOriginalStudent() {
	return originalStudent;
  }

  public void setOriginalStudent(Student originalStudent) {
	this.originalStudent = originalStudent;
  }
  
  @Override
  public void draw(Graphics g, Color color) {
   g.setColor(color);
   g.fillOval((int)this.getX(),(int)this.getY(),(int)this.radius,(int)this.radius);
  }
  
  public Rectangle getBoundingBox() {
	   return new Rectangle((int)(this.getX()), (int)(this.getY()), (int)this.radius, (int)this.radius);
  }
 }
