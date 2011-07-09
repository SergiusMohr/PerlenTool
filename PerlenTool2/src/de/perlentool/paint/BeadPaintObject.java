package de.perlentool.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class BeadPaintObject implements PaintObject {

	private int x;
	private int y;
	private int width;
	private int height;
	private final Color color;
	
	public BeadPaintObject(int x, int y, Color color) {
		this(x, y, 10, 10, color);
	}

	public BeadPaintObject(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.setHeight(height);
		this.color = color;
	}

	@Override
	public void paint(Graphics g, PaintContext context) {
		g.setColor(color);
		int px = x*context.getZoomFaktor();
		int py = y*context.getZoomFaktor();
		int pw = getWidth()*context.getZoomFaktor();
		int ph = getHeight()*context.getZoomFaktor();
		g.fillOval(px, py, pw, ph);
	}

	@Override
	public boolean matchesPosition(Point pos, Point posAbsolut) {
		if (pos.x == x && pos.y == y)
			return true;
		return false;
	}

	@Override
	public void paintHighlight(Graphics g, PaintContext context) {
		g.setColor(Color.WHITE);
		int px = x*context.getZoomFaktor();
		int py = y*context.getZoomFaktor();
		int pw = getWidth()*context.getZoomFaktor();
		int ph = getHeight()*context.getZoomFaktor();
		for (int i = 1; i < context.getZoomFaktor()+1; i++) {
			g.drawOval(px-i, py-i, pw+i*2, ph+i*2);
		}
	}

	@Override
	public void setPosition(Point pos) {
		x = pos.x;
		y = pos.y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
