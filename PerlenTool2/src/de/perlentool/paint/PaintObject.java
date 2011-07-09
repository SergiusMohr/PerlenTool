package de.perlentool.paint;

import java.awt.Graphics;
import java.awt.Point;


public interface PaintObject {

	public void paint(Graphics g, PaintContext context);

	public boolean matchesPosition(Point pos, Point posAbsolut);

	public void paintHighlight(Graphics g, PaintContext context);

	public void setPosition(Point pos);
}
