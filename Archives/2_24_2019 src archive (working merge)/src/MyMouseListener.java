import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

/**
 * MyMouseListener: This class will handle mouse events.
 * 
 * @author Artem
 * @author Anthony
 */
public class MyMouseListener implements MouseInputListener, MouseMotionListener, MouseWheelListener {
	private int x;
	private int y;
	private int clickX;
	private int clickY;
	private int releaseX;
	private int releaseY;
	private double zoomScale = 1;
	private boolean clickHandled;
	private boolean isDragging = false;
	private boolean isScrolling = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();
		this.clickHandled = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		releaseX = e.getX();
		releaseY = e.getY();
		this.isDragging = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		releaseX = e.getX();
		releaseY = e.getY();
		this.isDragging = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.
	 * MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) { // mouse wheel moved up
			for (int i = notches; i < 0; i++) {
				zoomScale += 0.1;
			}
			isScrolling = true;
		} else if (notches > 0) { // mouse wheel moved down
			for (int i = notches; i > 0; i--) {
				zoomScale -= 0.1;
			}
			isScrolling = true;
		} else { // mouse wheel remains the same
			isScrolling = false;
		}
		if (zoomScale < 1) {
			zoomScale = 1;
		} else if (zoomScale > 4) {
			zoomScale = 4;
		}
	}

	/**
	 * @return Point
	 */
	public Point getPos() {
		return new Point(x, y);
	}

	/**
	 * @return Point
	 */
	public Point getClick() {
		return new Point(clickX, clickY);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the clickX
	 */
	public int getClickX() {
		return clickX;
	}

	/**
	 * @param clickX the clickX to set
	 */
	public void setClickX(int clickX) {
		this.clickX = clickX;
	}

	/**
	 * @return the clickY
	 */
	public int getClickY() {
		return clickY;
	}

	/**
	 * @param clickY the clickY to set
	 */
	public void setClickY(int clickY) {
		this.clickY = clickY;
	}

	/**
	 * @return the releaseX
	 */
	public int getReleaseX() {
		return releaseX;
	}

	/**
	 * @param releaseX the releaseX to set
	 */
	public void setReleaseX(int releaseX) {
		this.releaseX = releaseX;
	}

	/**
	 * @return the releaseY
	 */
	public int getReleaseY() {
		return releaseY;
	}

	/**
	 * @param releaseY the releaseY to set
	 */
	public void setReleaseY(int releaseY) {
		this.releaseY = releaseY;
	}

	/**
	 * @return the zoomScale
	 */
	public double getZoomScale() {
		return zoomScale;
	}

	/**
	 * @param zoomScale the zoomScale to set
	 */
	public void setZoomScale(double zoomScale) {
		this.zoomScale = zoomScale;
	}

	/**
	 * This method will handle a click.
	 */
	public void clickHandled() {
		this.clickHandled = true;
	}

	/**
	 * @param clickHandled the clickHandled to set
	 */
	public void setClickHandled(boolean clickHandled) {
		this.clickHandled = clickHandled;
	}

	/**
	 * @return the isDragging
	 */
	public boolean isDragging() {
		return isDragging;
	}

	/**
	 * @param isDragging the isDragging to set
	 */
	public void setDragging(boolean isDragging) {
		this.isDragging = isDragging;
	}

	/**
	 * @return the isScrolling
	 */
	public boolean isScrolling() {
		return isScrolling;
	}

	/**
	 * @param isScrolling the isScrolling to set
	 */
	public void setScrolling(boolean isScrolling) {
		this.isScrolling = isScrolling;
	}

	/**
	 * @return boolean !(clickHandled)
	 */
	public boolean clickPending() {
		return (!this.clickHandled);
	}
}
