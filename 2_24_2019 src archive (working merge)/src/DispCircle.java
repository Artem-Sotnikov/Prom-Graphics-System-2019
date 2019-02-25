import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * DispCircle: This class will visually display a circle to the screen.
 * 
 * @author Artem
 * @author Anthony
 */
public class DispCircle extends Shape implements Serializable {
	private static final long serialVersionUID = 1L;

	private double radius;

	/**
	 * Class constructor.
	 */
	public DispCircle() {
	};

	/**
	 * Class constructor with parameters.
	 * 
	 * @param double x
	 * @param double y
	 * @param double radius
	 */
	public DispCircle(double x, double y, double radius) {
		super(x, y);
		this.radius = radius;
	}

	/**
	 * This method will return the radius.
	 * 
	 * @return double radius
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * This method will set the radius.
	 * 
	 * @param double rad
	 */
	public void setRadius(double rad) {
		this.radius = rad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Shape#draw(java.awt.Graphics, java.awt.Color)
	 */
	@Override
	public void draw(Graphics g, Color color) {
		g.setColor(color);
		g.fillOval((int) this.getX(), (int) this.getY(), (int) this.radius, (int) this.radius);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Shape#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getPrivateColor());
		g.fillOval((int) this.getX(), (int) this.getY(), (int) this.radius, (int) this.radius);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Shape#getBoundingBox()
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle((int) (this.getX()), (int) (this.getY()), (int) this.radius, (int) this.radius);
	}
}
