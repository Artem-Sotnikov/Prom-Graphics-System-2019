import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class GraphicTable extends Shape {
  private double width;
  private double height;
  
  public GraphicTable(double x, double y, double width, double height) {
   super(x, y);
   this.width = width;
   this.height = height;
  }
  
  public GraphicTable() {};
  
  public double getWidth() {
   return width;
  }

  public void setWidth(double width) {
   this.width = width;
  }

  public double getHeight() {
   return height;
  }

  public void setHeight(double height) {
   this.height = height;
  }

  
  @Override
  public void draw(Graphics g, Color color) {
   //Color IP_PURPLE = new Color(135,128,184);
   g.setColor(color);
   g.fillRect((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
  }

  @Override
  public Rectangle getBoundingBox() {
   return new Rectangle((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
  }
 }