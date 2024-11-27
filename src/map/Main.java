package map;

import java.awt.EventQueue;

import javafx.application.Application;

public class Main {

	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				MapTrackerUI ui = new MapTrackerUI();
//				ui.setVisible(true);
//			}
//		});
		MapTrackerUIFX.show(args);
	}

}
