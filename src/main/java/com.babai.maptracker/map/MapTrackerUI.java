package map;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

public class MapTrackerUI extends JFrame {
	
	private Image img;
	
	public MapTrackerUI() {
		//TODO window close handler asking to save
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Map Tracker");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		// Main contents
		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JLabel lblMapView = new JLabel();
		JPanel pnlForm = new JPanel();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize();
		mainPane.setDividerLocation(3 * size.width / 4);
		mainPane.setLeftComponent(lblMapView);
		mainPane.setRightComponent(pnlForm);
		setContentPane(mainPane);
		
		// Menu
		JMenu jmFile = new JMenu("File");
		JMenuItem jmiOpen = new JMenuItem("Open");
		JMenuItem jmiSave = new JMenuItem("Save");
		JMenuItem jmiExit = new JMenuItem("Exit");
		JMenu jmView = new JMenu("View");
		JMenuItem jmiZoomIn = new JMenuItem("Zoom In");
		JMenuItem jmiZoomOut = new JMenuItem("Zoom Out");
		jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		jmiOpen.addActionListener(e -> openImage(lblMapView));
		jmiSave.addActionListener(e -> save());
		jmiExit.addActionListener(e -> System.exit(0));
		jmFile.add(jmiOpen);
		jmFile.add(jmiSave);
		jmFile.add(jmiExit);
		JMenuBar bar = new JMenuBar();
		bar.add(jmFile);
		setJMenuBar(bar);
	}

	private void openImage(JLabel lblView) {
		Preferences prPref = Preferences.userNodeForPackage(MapTrackerUI.class);
		JFileChooser files = new JFileChooser(
				prPref.get("lastOpenDir", System.getProperty("user.home")));
		if (files.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File f = files.getSelectedFile();
			if (f == null) {
				return;
			}
			
			try {
				img = ImageIO.read(f);
				lblView.setIcon(new ImageIcon(img));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				prPref.put("lastOpenDir", f.getParent());
			}
		}
	}
	
	private void save() {
		// TODO Auto-generated method stub
	}
}
