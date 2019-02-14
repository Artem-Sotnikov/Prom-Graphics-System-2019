import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

abstract class Shape {
  private double x;
  private double y;
  
  public Shape(double x, double y) {
   this.x = x;
   this.y = y;
  }
  
  public Shape() {};
  
  abstract void draw(Graphics g, Color color); 
  

  /**
   * @return the x
   */
  public double getX() {
   return x;
  }

  /**
   * @return the y
   */
  public double getY() {
   return y;
  }  
  
  public void setX(double input) {
	  this.x = input;
  }
  
  public void setY(double input) {
	  this.y = input;
  }
  
  public abstract Rectangle getBoundingBox();
 }
