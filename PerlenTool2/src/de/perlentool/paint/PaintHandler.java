package de.perlentool.paint;

import java.awt.Point;

import de.perlentool.Constants;

public class PaintHandler {
	private static PaintHandler instance = new PaintHandler();

	public static PaintHandler getInstance() {
		return instance;
	}

	/**
	 * @param context
	 * @param pos relative Position (entspricht der ohne Zoom)
	 * @return true, wenn was gemacht wurde und ein repaint noetig ist
	 */
	public boolean handleMouseClick(PaintContext context, Point pos, Point posAbsolut) {
		if (context.getCurrentTool() == Constants.TOOL_BEAD) {
			BeadPaintObject bead = new BeadPaintObject(pos.x, pos.y, context.getCurrentColor());
			Integer width = (Integer) context.getDetails().get("width");
			if (width != null) {
				bead.setWidth(10*width.intValue()*context.getZoomFaktor());
			}
			Integer height = (Integer) context.getDetails().get("height");
			if (height != null) {
				bead.setHeight(10*height.intValue()*context.getZoomFaktor());
			}
			context.getPaintObjects().add(bead);
			return true;
		} else if (context.getCurrentTool() == Constants.TOOL_SELECT) {
			//Die letzten Elemente sind ggf. ueber die ersten gemalt, sollen zuerst selektierbar sein
			for (int i = context.getPaintObjects().size()-1; i >= 0; i--) {
				PaintObject paintObject = context.getPaintObjects().get(i);
				if (paintObject.matchesPosition(pos, posAbsolut)) {
					context.setSelectedPaintObject(paintObject);
					return true;
				}
			}
			context.setSelectedPaintObject(null);
		}
		return false;
	}
}
