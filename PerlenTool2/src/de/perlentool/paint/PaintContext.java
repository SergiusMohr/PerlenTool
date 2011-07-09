package de.perlentool.paint;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jgoodies.binding.beans.Model;

import de.perlentool.Constants;


/**
 * Container fuer allgemein benoetigte Daten, wie die zu zeichnenden Objekte 
 * @author sergius
 *
 */
public class PaintContext extends Model {

	private static final long serialVersionUID = -4421163028376823456L;
	
	private int width;
	private int height;
	private List<PaintObject> paintObjects = new ArrayList<PaintObject>();
	private PaintObject raster = new RasterPaintObject();
	private Integer zoomFaktor = 1;
	private boolean onLine = false; //Perlen auf der Linie platzieren?
	
	private boolean highlightMousePosition = false;
	private Point currentMousePosition;
	
	private Color currentColor = Color.BLACK;
	private int currentTool = Constants.TOOL_BEAD;
	private PaintObject selectedPaintObject;
	private Map<String, Object> details;
	
	public PaintContext() {
		resetDetails();
	}

	public List<PaintObject> getPaintObjects() {
		return paintObjects;
	}

	public void setPaintObjects(List<PaintObject> paintObjects) {
		this.paintObjects = paintObjects;
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

	public PaintObject getRaster() {
		return raster;
	}

	public void setRaster(PaintObject raster) {
		this.raster = raster;
	}

	public Integer getZoomFaktor() {
		return zoomFaktor;
	}

	public void setZoomFaktor(Integer zoomFaktor) {
		Integer oldValue = this.zoomFaktor;
		this.zoomFaktor = zoomFaktor;
		firePropertyChange("zoomFaktor", oldValue, zoomFaktor);
	}

	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		boolean oldValue = this.onLine;
		this.onLine = onLine;
		firePropertyChange("onLine", oldValue, onLine);
	}

	public boolean isHighlightMousePosition() {
		return highlightMousePosition;
	}

	public void setHighlightMousePosition(boolean highlightMousePosition) {
		this.highlightMousePosition = highlightMousePosition;
	}

	public Point getCurrentMousePosition() {
		return currentMousePosition;
	}

	public void setCurrentMousePosition(Point currentMousePosition) {
		this.currentMousePosition = currentMousePosition;
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public int getCurrentTool() {
		return currentTool;
	}

	public void setCurrentTool(int currentTool) {
		this.currentTool = currentTool;
	}

	public PaintObject getSelectedPaintObject() {
		return selectedPaintObject;
	}

	public void setSelectedPaintObject(PaintObject selectedPaintObject) {
		this.selectedPaintObject = selectedPaintObject;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

	public void resetDetails() {
		this.details = new HashMap<String, Object>();
	}
}
