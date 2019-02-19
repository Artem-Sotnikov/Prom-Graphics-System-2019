import static constant.Constants.*;

import java.awt.Color;
import java.awt.Graphics;

public class DispTable extends DispRectangle {
	private static final long serialVersionUID = 1L;
	
	private Table originalTable;
	private boolean isRound;
	
	public Table getOriginalTable() {
		return originalTable;
	}

	public void setOriginalTable(Table originalTable) {
		this.originalTable = originalTable;
	}

	DispTable() {
		this.setHovered(false);
		this.setHighlighted(false);
		this.setReal(false);
		this.setSelected(false);	
		this.setRound(false);
	}
	
	public boolean isRound() {
		return isRound;
	}

	public void setRound(boolean isRound) {
		this.isRound = isRound;
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
	
	@Override
	public void draw(Graphics g, Color color) {	
		g.setColor(color);
		
		if (this.isRound()) {
			g.fillOval((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
		} else {
			g.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
		}
	}	
	
	public void drawReduced(Graphics g, Color color, int reductionSize) {
		g.setColor(color);
		
		if (this.isRound()) {
			g.fillOval((int)this.getX() + reductionSize,(int)this.getY() + reductionSize,
					(int)this.getWidth() - reductionSize*2,(int)this.getHeight() - reductionSize*2);
		} else {
			g.fillRect((int)this.getX() + reductionSize,(int)this.getY() + reductionSize,
					(int)this.getWidth() - reductionSize*2,(int)this.getHeight() - reductionSize*2);
		}
	}
	
	public void drawObject(Graphics g) {
		if (this.isHovered()) {					
					
			if (this.isSelected()) {
				this.draw(g,Color.MAGENTA);
			} else if (this.isHighlighted()) {
				this.draw(g,Color.GREEN);
			} else if (this.isReal()) {
				this.draw(g,Color.MAGENTA);
			}
						
			
			if (this.isSelected()) {
				this.drawReduced(g,Color.YELLOW,5);
			} else if (this.isReal()) {
				this.drawReduced(g,IP_PURPLE,5);
			} else {
				this.drawReduced(g,LIGHT_GRAY,5);
			}

		
	        if ((this.isHighlighted()) && (!this.isSelected())) {
								
				this.drawReduced(g,Color.GREEN,8);				
				
			}
	
		} else if (this.isSelected()) {
			this.draw(g,Color.YELLOW);			
		} else if (this.isHighlighted()) {
			
			this.draw(g,Color.GREEN);
			
			if (this.isReal()) {
				this.drawReduced(g,IP_PURPLE,5);
			} else {
				this.drawReduced(g,LIGHT_GRAY,5);
			}

			
		} else {
			if (this.isReal()) {
				this.draw(g,IP_PURPLE);
			} else {
				this.draw(g,LIGHT_GRAY);
			}
		}
	}
}




