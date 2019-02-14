import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class DispCircle extends Shape {
	private double radius;

	public DispCircle(double x, double y, double radius) {
		super(x, y);
		this.radius = radius;
	}

	public DispCircle() {};

	public double getRadius() {
		return this.radius;
	}  

	public void setRadius(double rad) {
		this.radius = rad;
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
