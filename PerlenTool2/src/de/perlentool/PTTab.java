package de.perlentool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import de.perlentool.paint.PaintContext;

public class PTTab extends JPanel {

	private static final long serialVersionUID = -7113414637279068862L;

	private final JScrollPane scrollPaneCanvas;
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.RES_FILE);

	private final JToggleButton selectButton;

	private final JToggleButton beadButton;

	private final JPanel zoomPanel;
	
	public PTTab() {
		final PaintContext context = new PaintContext();
		context.setWidth(450);
		context.setHeight(700);
		final PTCanvas canvas = new PTCanvas(context);
		canvas.addMouseListener(new MouseEventHandler(context));
		canvas.addMouseMotionListener(new MouseMotionHandler(context));
		scrollPaneCanvas = new JScrollPane(canvas);
		
		
		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new GridLayout(2, 2));

		ButtonGroup toolGroup = new ButtonGroup();
		
		selectButton = new JToggleButton(new ImageIcon(getClass().getClassLoader().getResource("de/perlentool/images/select.png")));
		selectButton.setToolTipText(resourceBundle.getString("tool.select"));
//		selectButton.setMaximumSize(new Dimension(25, 25));
		toolPanel.add(selectButton);
		toolGroup.add(selectButton);
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				context.setCurrentTool(Constants.TOOL_SELECT);
				context.resetDetails();
				canvas.recreateCursor();
			}
		});
		beadButton = new JToggleButton(new ImageIcon(getClass().getClassLoader().getResource("de/perlentool/images/bead.png")), true);
		beadButton.setToolTipText(resourceBundle.getString("tool.bead"));
		toolPanel.add(beadButton);
		toolGroup.add(beadButton);
		beadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				context.setCurrentTool(Constants.TOOL_BEAD);
				context.setSelectedPaintObject(null);
				canvas.recreateCursor();
			}
		});
		
		
		PresentationModel<PaintContext> model = new PresentationModel<PaintContext>(context);
		JComboBox zoomComboBox = BasicComponentFactory.createComboBox(new SelectionInList<Integer>(
				new Integer[] {Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4) }, 
				model.getComponentModel("zoomFaktor")));
		zoomComboBox.setName("zoomFaktor"); //for validation, debugging etc.
		zoomComboBox.setToolTipText(resourceBundle.getString("zoomFaktor.tooltip"));
		zoomComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setSize(context.getWidth()*context.getZoomFaktor(), context.getHeight()*context.getZoomFaktor());
				canvas.repaint();
			}
		});	
		JLabel zoomIcon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("de/perlentool/images/lupe2.png")));
		zoomIcon.setToolTipText(zoomComboBox.getToolTipText());
		zoomPanel = new JPanel();
		zoomPanel.add(zoomIcon);
		zoomPanel.add(zoomComboBox);
		toolPanel.add(zoomPanel);
		
		
		JPanel anotherElementsPanel = new JPanel();
		anotherElementsPanel.setLayout(new GridLayout(0,1));
		

		JButton colorButton = new JButton();
		colorButton.setBackground(context.getCurrentColor());
		colorButton.setToolTipText(resourceBundle.getString("color.tooltip"));
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color jetzt=JColorChooser.showDialog(PTTab.this, resourceBundle.getString("colorchooser.title"), context.getCurrentColor());
				if (jetzt != null) {
					((JComponent)e.getSource()).setBackground(jetzt);
					context.setCurrentColor(jetzt);
					canvas.recreateCursor();
				}
			}
		});
		anotherElementsPanel.add(colorButton);
		
		JCheckBox onLine = BasicComponentFactory.createCheckBox(model.getModel("onLine"), resourceBundle.getString("onLine"));
		onLine.setToolTipText(resourceBundle.getString("onLine.tooltip"));
		onLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.repaint();
			}
		});
		anotherElementsPanel.add(onLine);
		
		
		JPanel toolDetailsPanel = new JPanel();
		
		JSlider breite = new JSlider(1, 10, 1);
		breite.setToolTipText("Breite");
		breite.setPaintLabels(true);
		breite.setPaintTicks(true);
		breite.setMajorTickSpacing(2);
		breite.setMinorTickSpacing(1);
		breite.setSnapToTicks(true);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(Integer.valueOf(1), new JLabel("1"));
		labelTable.put(Integer.valueOf(5), new JLabel("5"));
		labelTable.put(Integer.valueOf(10), new JLabel("10"));
		breite.setLabelTable(labelTable);
		breite.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				context.getDetails().put("width", source.getValue());
				canvas.repaint();
			}
		});
		JPanel breitePanel = new JPanel();
		breitePanel.setBorder(new TitledBorder("Breite"));
		breitePanel.add(breite);
		toolDetailsPanel.add(breitePanel);
		
		JSlider hoehe = new JSlider(1, 10, 1);
		hoehe.setPaintLabels(true);
		hoehe.setPaintTicks(true);
		hoehe.setLabelTable(labelTable);
		hoehe.setToolTipText("Höhe");
		hoehe.setMajorTickSpacing(2);
		hoehe.setMinorTickSpacing(1);
		hoehe.setSnapToTicks(true);
		hoehe.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				context.getDetails().put("height", source.getValue());
				canvas.repaint();
			}
		});
		JPanel hoehePanel = new JPanel();
		hoehePanel.setBorder(new TitledBorder("Höhe"));
		hoehePanel.add(hoehe);
		toolDetailsPanel.add(hoehePanel);
		
		
		
		
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		left.add(scrollPaneCanvas, BorderLayout.CENTER);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(left);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(toolPanel);
		rightPanel.add(anotherElementsPanel);
		rightPanel.add(toolDetailsPanel);
		add(rightPanel);
	}
	
	
//	public JComponent createRightLayout() {
//		
//		FormLayout layout = new FormLayout(
//	          "right:max(40dlu;pref), 3dlu, 80dlu, 7dlu, " // 1st major colum
//	        + "80dlu",        // 2nd major column
//	          "");                                         // add rows dynamically
//      DefaultFormBuilder builder = new DefaultFormBuilder(layout, GUIHelper.getResourceBundle());
//      builder.setDefaultDialogBorder();
//      builder.nextColumn();
//      builder.nextColumn();
//      builder.appendI15dTitle("beratung.mandant");
//      builder.appendI15dTitle("beratung.partner");
//      builder.nextLine();
//      builder.appendUnrelatedComponentsGapRow();
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("mandant", MandantBusinessObject.ANREDE.getName()), anredeMandant);
//      builder.append(anredePartner);
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("mandant", MandantBusinessObject.VORNAME), vornameMandant);
//      builder.append(vornamePartner);
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("mandant", MandantBusinessObject.NACHNAME), nachnameMandant);
//      builder.append(nachnamePartner);
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("mandant", MandantBusinessObject.GEBURTSDATUM), geburtsdatumMandant);
//      builder.append(geburtsdatumPartner);
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("mandant", MandantBusinessObject.STRASSE), strasseMandant);
//      builder.append(strassePartner);
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("mandant", MandantBusinessObject.POSTLEITZAHL), postleitzahlMandant);
//      builder.append(postleitzahlPartner);
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("mandant", MandantBusinessObject.ORT), ortMandant);
//      builder.append(ortPartner);
//      builder.nextLine();
//      builder.appendUnrelatedComponentsGapRow();
//      builder.nextLine();
//      builder.append(GUIHelper.getFieldName("beratung", "anlegermentalitaet"), anlegermentalitaet);
//      builder.nextLine();
//
////      ButtonBarBuilder2 buttonBuilder = new ButtonBarBuilder2();
////      buttonBuilder.addGlue();
////      buttonBuilder.addButton(saveButton);
////      
////      builder.nextLine();
////      builder.appendGlueRow();
////      builder.append(buttonBuilder.getPanel(), 5);
//
//		return builder.getPanel();
//	}

}
