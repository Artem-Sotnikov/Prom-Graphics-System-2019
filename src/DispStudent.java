import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import static constant.Constants.*;


public class DispStudent extends DispCircle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Student originalStudent;
	
	public DispStudent() {
		this.setHovered(false);
		this.setHighlighted(false);
		this.setReal(false);
		this.setSelected(false);		
	}
	
	public DispStudent(double x, double y, double radius, Student originalStudent) {
		super(x, y, radius);
		this.originalStudent = originalStudent;
	}

	public Student getOriginalStudent() {
		return originalStudent;
	}

	public void setOriginalStudent(Student originalStudent) {
		this.originalStudent = originalStudent;
	}
	
	@Override
	public String toString() {
		return "X: " + this.getX() + "\nY: " + this.getY() + "\nRadius: " + this.getRadius() + "\nName: " + this.originalStudent.getName();
	}
		
	public void drawBox(Graphics g) {
		if (this.isReal()) {
			DispRectangle infoBox = new DispRectangle();
			infoBox.setX(this.getX() - SCALE_FACTOR*4 - 2);
		    infoBox.setY(this.getY() - SCALE_FACTOR*2 - 2);
		    infoBox.setWidth(SCALE_FACTOR*4);
		    infoBox.setHeight(SCALE_FACTOR*2);
		    infoBox.draw(g,Color.MAGENTA);
		    g.setColor(Color.BLACK);
		    g.drawString(this.getOriginalStudent().getName(),(int)infoBox.getX() + 5,(int)infoBox.getY() + 15);
		}
	}
	
	public void drawObject(Graphics g) {
		if (this.isHovered()) {					
			
			if (this.isSelected()) {
				this.draw(g,Color.MAGENTA);
			} else if (this.isHighlighted()) {
				this.draw(g,Color.GREEN);
			} else {
				this.draw(g,Color.MAGENTA);
			}
						
			
			if (this.isSelected()) {
				g.setColor(Color.YELLOW);
			} else if (this.isReal()) {
				g.setColor(Color.BLUE);
			} else {
				g.setColor(LIGHT_GRAY);
			}

	        g.fillOval((int)this.getX() + 3,(int)this.getY() + 3,
	          (int)this.getRadius() - 6,(int)this.getRadius() - 6);
			
	        if (this.isSelected()) {
	        	
	        } else if (this.isHighlighted()) {
				
				g.setColor(Color.GREEN);
				g.fillOval((int)this.getX() + 5,(int)this.getY() + 5,
				          (int)this.getRadius() - 10,(int)this.getRadius() - 10);
			
			}

		} else if (this.isSelected()) {
			this.draw(g,Color.YELLOW);			
		} else if (this.isHighlighted()) {
			//System.out.println("It works, huh...");
			
			this.draw(g,Color.GREEN);
			
			if (this.isReal()) {
				g.setColor(Color.BLUE);
			} else {
				g.setColor(LIGHT_GRAY);
			}

	        g.fillOval((int)this.getX() + 3,(int)this.getY() + 3,
	          (int)this.getRadius() - 6,(int)this.getRadius() - 6);
			
		} else {
			if (this.isReal()) {
				this.draw(g,Color.BLUE);
			} else {
				this.draw(g,LIGHT_GRAY);
			}
		}
	}
	
	
	
}