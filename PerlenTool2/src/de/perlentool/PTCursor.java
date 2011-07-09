package de.perlentool;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import de.perlentool.paint.PaintContext;

public class PTCursor {

	private static final long serialVersionUID = -5279999707389965927L;

	private static final int[] cursorPolygonX = new int[] {0,0,4,7,10,7,11,1};
	private static final int[] cursorPolygonY = new int[] {0,15,11,18,17,10,10,0};
	private static final Point hotspot = new Point(0, 0); //mit welchem Punkt die Maus zielt
	
	public static Cursor createCursor(PaintContext context) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		Image image = new BufferedImage(30, 30, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = image.getGraphics();
		
		//Cursor malen 
		g.setColor(Color.WHITE);
		g.fillPolygon(cursorPolygonX, cursorPolygonY, cursorPolygonX.length);
		g.setColor(Color.BLACK);
		g.drawPolygon(cursorPolygonX, cursorPolygonY, cursorPolygonX.length);
		
		int px = 15;
		int py = 15;
		if (context.getCurrentTool() == Constants.TOOL_BEAD) {
//			g.setColor(context.getCurrentColor()); // TODO Problems with more than two Colors!?
			g.setColor(Color.WHITE);
			g.fillOval(px, py, 10, 10);
			g.setColor(Color.BLACK);
			g.drawOval(px, py, 10, 10);
		}

		return toolkit.createCustomCursor(
			    image, hotspot, "BeadToolCursor");

	}

}
