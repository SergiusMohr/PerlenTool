package de.perlentool.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class RasterPaintObject implements PaintObject {

	@Override
	public void paint(Graphics g, PaintContext context) {
		g.setColor(new Color(153,153,153));
		int step = context.getZoomFaktor()*10;
		int width = context.getWidth()*context.getZoomFaktor();
		int height = context.getHeight()*context.getZoomFaktor();
		for (int i=0;i<width;i+=step) {
			g.drawLine(i,0,i,height);  
		}
		for (int i=0;i<height;i+=step) {   
			g.drawLine(0,i,width,i);
		}
	}

	@Override
	public boolean matchesPosition(Point pos, Point posAbsolut) {
		return false;
	}

	@Override
	public void paintHighlight(Graphics g, PaintContext context) {
		//nothing
	}

	@Override
	public void setPosition(Point pos) {
		//nothing
	}

}
