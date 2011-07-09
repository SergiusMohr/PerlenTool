package de.perlentool;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import de.perlentool.paint.PaintContext;

public class MouseMotionHandler extends MouseMotionAdapter {

	private final PaintContext context;

	public MouseMotionHandler(PaintContext context) {
		this.context = context;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		context.setHighlightMousePosition(true);
		context.setCurrentMousePosition(e.getPoint());
		e.getComponent().repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (context.getCurrentTool() == Constants.TOOL_SELECT && context.getSelectedPaintObject() != null) {
			Point pos = PTCanvas.convertToRelativePosition(e.getPoint(), context);
			if (pos.x < context.getWidth() && pos.y < context.getHeight()) {
				context.getSelectedPaintObject().setPosition(pos);
			}
			e.getComponent().repaint();
		}
	}
}
