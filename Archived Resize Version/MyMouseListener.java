import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

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

	public void clickHandled() {
		this.clickHandled = true;
	}

	public boolean clickPending() {
		return (!this.clickHandled);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();
		this.clickHandled = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		releaseX = e.getX();
		releaseY = e.getY();
		this.isDragging = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		releaseX = e.getX();
		releaseY = e.getY();
		this.isDragging = true;
	}

	public Point getClick() {
		return new Point(clickX, clickY);
	}


	public int getReleaseX() {
		return releaseX;
	}

	public void setReleaseX(int releaseX) {
		this.releaseX = releaseX;
	}

	public double getZoomScale() {
		return zoomScale;
	}

	public void setZoomScale(double zoomScale) {
		this.zoomScale = zoomScale;
	}

	public int getReleaseY() {
		return releaseY;
	}

	public void setReleaseY(int releaseY) {
		this.releaseY = releaseY;
	}

	public Point getPos() {
		return new Point(x, y);
	}

	public boolean isDragging() {
		return this.isDragging;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) { // mouse wheel moved up
			for (int i = notches; i < 0; i++) {
				zoomScale += 0.1;
			}
		} else if (notches > 0) { // mouse wheel moved down
			for (int i = notches; i > 0; i--) {
				zoomScale -= 0.1;
			}
		}
		if (zoomScale < 1) {
			zoomScale = 1;
		} else if (zoomScale > 4) {
			zoomScale = 4;
		}
	}
}
