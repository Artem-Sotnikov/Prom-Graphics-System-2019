import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * DispRectangle: This class will visually display a rectangle to the screen.
 * 
 * @author Artem
 * @author Anthony
 */
public class DispRectangle extends Shape implements Serializable {
	private static final long serialVersionUID = 1L;

	private double width;
	private double height;

	/**
	 * Class constructor.
	 */
	public DispRectangle() {
	};

	/**
	 * Class constructor with parameters.
	 * 
	 * @param x double
	 * @param y double
	 * @param width double
	 * @param height double
	 */
	public DispRectangle(double x, double y, double width, double height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Shape#draw(java.awt.Graphics, java.awt.Color)
	 */
	@Override
	public void draw(Graphics g, Color color) {
		g.setColor(color);
		g.fillRect((int) this.getX(), (int) this.getY(), (int) this.width, (int) this.height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Shape#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getPrivateColor());
		g.fillRect((int) this.getX(), (int) this.getY(), (int) this.width, (int) this.height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Shape#getBoundingBox()
	 */
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle((int) this.getX(), (int) this.getY(), (int) this.width, (int) this.height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "X: " + this.getX() + "\nY: " + this.getY() + "\nWidth: " + this.width + "\nHeight: " + this.height;
	}
}