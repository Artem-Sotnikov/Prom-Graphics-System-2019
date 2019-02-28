import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

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
	
	public Shape() {};

	abstract void draw(Graphics g, Color color); 
	abstract void draw(Graphics g);
	
	public boolean isReal() {
		return this.real;
	}
	
	public void setReal(boolean r) {
		this.real = r;
	}
	
	public Color getPrivateColor() {
		return privateColor;
	}
	
	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}	
	
	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
		this.referenceNumber = 0;
	}	
	
	public void setPrivateColor(Color privateColor) {
		this.privateColor = privateColor;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}  

	public void setX(double input) {
		this.x = input;
	}

	public void setY(double input) {
		this.y = input;
	}

	public int getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(int referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public abstract Rectangle getBoundingBox();
}
