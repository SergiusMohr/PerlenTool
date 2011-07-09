package de.perlentool;

import de.perlentool.paint.PaintContext;

public interface Storage {
	public void storeKeyValue(String key, Object value) throws Exception;
	public Object loadKeyValue(String key) throws Exception;
	public void storeContext(PaintContext context) throws Exception;
	public PaintContext loadContext() throws Exception;
}
