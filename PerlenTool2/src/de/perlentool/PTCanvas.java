package de.perlentool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.Scrollable;

import de.perlentool.paint.PaintContext;
import de.perlentool.paint.PaintObject;

public class PTCanvas extends JComponent implements Scrollable, Printable {

	private static final long serialVersionUID = 7087335366600582718L;
	private final PaintContext context;

	public PTCanvas(PaintContext context) {
		this.context = context;
		setSize(context.getWidth(), context.getHeight());
		setDoubleBuffered(true);
		recreateCursor();
	}
	
	public void recreateCursor() {
		setCursor(PTCursor.createCursor(context));
		repaint();
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		if (pageIndex >= 1)
			return Printable.NO_SUCH_PAGE;
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.translate(pf.getImageableX(),pf.getImageableY());
		paint(g2);
		return Printable.PAGE_EXISTS;
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
//		return perlenGroesse*5;
		return 50;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
//		return perlenGroesse*5;
		return visibleRect.height;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(context.getWidth()*context.getZoomFaktor().intValue(), context.getHeight()*context.getZoomFaktor().intValue());
	}
	
//	@Override
//	public Dimension getMaximumSize() {
//		return getPreferredSize();
//	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (context.getRaster() != null) {
			context.getRaster().paint(g, context);
		}
		
		if (context.isHighlightMousePosition() && context.getCurrentMousePosition() != null) {
			Point pos = convertToRelativePosition(context.getCurrentMousePosition(), context);
			if (pos.x < context.getWidth() && pos.y < context.getHeight()) {
				int px = pos.x*context.getZoomFaktor();
				int py = pos.y*context.getZoomFaktor();
				int pw = 10*context.getZoomFaktor();
				int ph = 10*context.getZoomFaktor();
				Integer width = (Integer) context.getDetails().get("width");
				if (width != null) {
					pw = pw*width.intValue();
				}
				Integer height = (Integer) context.getDetails().get("height");
				if (height != null) {
					ph = ph*height.intValue();
				}
				g.setColor(Color.YELLOW);
				g.fillRect(px, py, pw, ph);
				g.setColor(Color.BLACK);
				g.drawRect(px, py, pw, ph);
			}
		}
	}

	@Override
	protected void paintChildren(Graphics g) {
		if (context.getPaintObjects() != null) {
			for (Iterator<PaintObject> it = context.getPaintObjects().iterator(); it.hasNext();) {
				PaintObject paintObject = it.next();
				paintObject.paint(g, context);
				
			}
		}
		if (context.getSelectedPaintObject() != null && context.getCurrentTool() == Constants.TOOL_SELECT) { //highlight selection
			context.getSelectedPaintObject().paintHighlight(g, context);
		}		
		super.paintChildren(g);
	}
	
//	@Override
//	public void paint(Graphics g) {
////		super.paint(g);
//		if (context.getRaster() != null) {
//			context.getRaster().paint(g, context);
//		}
//		
//		if (context.isHighlightMousePosition() && context.getCurrentMousePosition() != null) {
//			g.setColor(Color.WHITE);
//			g.fillRect(context.getCurrentMousePosition().x, context.getCurrentMousePosition().y, 10*context.getZoomFaktor(), 10*context.getZoomFaktor());
//		}
//		
//		if (context.getPaintObjects() != null) {
//			for (Iterator<PaintObject> it = context.getPaintObjects().iterator(); it.hasNext();) {
//				PaintObject paintObject = it.next();
//				paintObject.paint(g, context);
//				
//			}
//		}
//	}

	public static Point convertToRelativePosition(Point p, PaintContext context) {
		if (p == null || context == null)
			return null;
		
		int zoom = context.getZoomFaktor();
		int eX=p.x/zoom;
		int eY=p.y/zoom;
		
		int rnd = 10; 
		if (context.isOnLine())
			rnd = 5;
		//gerundet, die letzte kommastelle weggemacht
		int roundX=((eX/rnd)*rnd);
		int roundY=((eY/rnd)*rnd);
		return new Point(roundX, roundY);
	}
}
