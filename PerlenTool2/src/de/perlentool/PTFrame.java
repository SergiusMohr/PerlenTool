package de.perlentool;

import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Swing-Anwendung
 * @author sergius
 *
 */
public class PTFrame extends JFrame {

	private static final long serialVersionUID = -6269659372204942412L;
	private final JTabbedPane jTabbedPane;
	
	/**
	 * @throws HeadlessException
	 */
	public PTFrame() throws HeadlessException {
		setTitle(ResourceBundle.getBundle(Constants.RES_FILE).getString("title")+" "+Constants.VERSION+" "+Constants.PROGRAM_INFO);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		jTabbedPane = new JTabbedPane();
		add(jTabbedPane);
		addNewTab();
	}

	public void addNewTab() {
		JPanel tab = new PTTab();
		String title = ResourceBundle.getBundle(Constants.RES_FILE).getString("tabTitle");
		title+= " "+(jTabbedPane.getTabCount()+1);
		jTabbedPane.addTab(title, tab);
		
	}
}
