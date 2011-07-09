package de.perlentool;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.perlentool.paint.PaintContext;
import de.perlentool.paint.PaintHandler;


public class MouseEventHandler extends MouseAdapter {

	private final PaintContext context;

	public MouseEventHandler(PaintContext context) {
		this.context = context;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point pos = PTCanvas.convertToRelativePosition(e.getPoint(), context);
		if (pos.x < context.getWidth() && pos.y < context.getHeight()) {
			if (PaintHandler.getInstance().handleMouseClick(context, pos, e.getPoint())) {
				e.getComponent().repaint();
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		context.setHighlightMousePosition(true);
		context.setCurrentMousePosition(e.getPoint());
		e.getComponent().repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		context.setHighlightMousePosition(false);
		context.setCurrentMousePosition(null);
		e.getComponent().repaint();
	}
	
}
