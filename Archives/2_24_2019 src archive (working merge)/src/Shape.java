import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * This abstract class will define the structure for shapes.
 * 
 * @author Artem
 * @author Anthony
 */
public abstract class Shape implements Serializable {
	private static final long serialVersionUID = 1L;

	private double x;
	private double y;
	private int referenceNumber;
	private Color privateColor;

	private boolean isHighlighted;
	private boolean isSelected;
	private boolean isHovered;
	private boolean real;

	/**
	 * Class constructor.
	 */
	public Shape() {
	};

	/**
	 * Class constructor with parameters.
	 * 
	 * @param double x
	 * @param double y
	 */
	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
		this.referenceNumber = 0;
	}

	/**
	 * This method will draw a shape with a custom color.
	 * 
	 * @param g
	 * @param color
	 */
	abstract void draw(Graphics g, Color color);

	/**
	 * This method will draw a shape.
	 * 
	 * @param g
	 */
	abstract void draw(Graphics g);

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the referenceNumber
	 */
	public int getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(int referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * @return the privateColor
	 */
	public Color getPrivateColor() {
		return privateColor;
	}

	/**
	 * @param privateColor the privateColor to set
	 */
	public void setPrivateColor(Color privateColor) {
		this.privateColor = privateColor;
	}

	/**
	 * @return the isHighlighted
	 */
	public boolean isHighlighted() {
		return isHighlighted;
	}

	/**
	 * @param isHighlighted the isHighlighted to set
	 */
	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return the isHovered
	 */
	public boolean isHovered() {
		return isHovered;
	}

	/**
	 * @param isHovered the isHovered to set
	 */
	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}

	/**
	 * @return the real
	 */
	public boolean isReal() {
		return real;
	}

	/**
	 * @param real the real to set
	 */
	public void setReal(boolean real) {
		this.real = real;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return Rectangle
	 */
	public abstract Rectangle getBoundingBox();
}
