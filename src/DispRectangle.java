import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

public class DispRectangle extends Shape implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private double width;
	private double height;

	public DispRectangle(double x, double y, double width, double height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	public DispRectangle() {};

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
		g.setColor(color);
		g.fillRect((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle((int)this.getX(), (int)this.getY(), (int)this.width, (int)this.height);
	}
	
	@Override
	public String toString() {
		return "X: " + this.getX() + "\nY: " + this.getY() + "\nWidth: " + this.width + "\nHeight: " + this.height;
	}
}