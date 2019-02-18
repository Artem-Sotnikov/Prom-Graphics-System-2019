import static constant.Constants.*;

import java.awt.Color;
import java.awt.Graphics;

public class DispTable extends DispRectangle {
	
	DispTable() {
		this.setHovered(false);
		this.setHighlighted(false);
		this.setReal(false);
		this.setSelected(false);	
	}
	
	public void drawBox(Graphics g, int tableNumber) {
		if (this.isReal()) {
			DispRectangle infoBox = new DispRectangle();
			infoBox.setX(this.getX() - SCALE_FACTOR*4 - 2);
		    infoBox.setY(this.getY() - SCALE_FACTOR*2 - 2);
		    infoBox.setWidth(SCALE_FACTOR*4);
		    infoBox.setHeight(SCALE_FACTOR*2);
		    infoBox.draw(g,Color.MAGENTA);
		    g.setColor(Color.BLACK);
		    g.drawString("Table " + Integer.toString(tableNumber),(int)infoBox.getX() + 5,(int)infoBox.getY() + 15);
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
				g.setColor(IP_PURPLE);
			} else {
				g.setColor(LIGHT_GRAY);
			}

	        g.fillRect((int)this.getX() + 5,(int)this.getY() + 5,
	          (int)this.getWidth() - 10,(int)this.getHeight() - 10);
			
	        if ((this.isHighlighted()) && (!this.isSelected())) {
				
				g.setColor(Color.GREEN);
				g.fillRect((int)this.getX() + 8,(int)this.getY() + 8,
				          (int)this.getWidth() - 16,(int)this.getHeight() - 16);
			}
	
		} else if (this.isSelected()) {
			this.draw(g,Color.YELLOW);			
		} else if (this.isHighlighted()) {
			
			this.draw(g,Color.GREEN);
			
			if (this.isReal()) {
				g.setColor(IP_PURPLE);
			} else {
				g.setColor(LIGHT_GRAY);
			}

			g.fillRect((int)this.getX() + 5,(int)this.getY() + 5,
			          (int)this.getWidth() - 10,(int)this.getHeight() - 10);
				
		} else {
			if (this.isReal()) {
				this.draw(g,IP_PURPLE);
			} else {
				this.draw(g,LIGHT_GRAY);
			}
		}
	}
}




