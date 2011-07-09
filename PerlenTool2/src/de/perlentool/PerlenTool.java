package de.perlentool;

import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

/**
 * 
 * @author sergius
 *
 */
public class PerlenTool {

	/**
	 * @param args String[] 1.Argument sollte die zu ladene Datei sein (um einen Dateityp zu registrieren)
	 */
	public static void main(String[] args) {

		try {
//			PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
			
			//Synthetica is a cool design, but check the licence, its not free
//			UIManager.put("Synthetica.extendedFileChooser.rememberPreferences", Boolean.FALSE); //Bug beim FileChooser, haengt sich sonst manchmal auf
//			UIManager.put("Synthetica.window.decoration", Boolean.FALSE); //muss vor dem Layout gesetzt werden!
//			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PTFrame frame = new PTFrame();
		frame.pack();
		frame.setSize(640, 450);
		frame.setVisible(true);
	}

}
