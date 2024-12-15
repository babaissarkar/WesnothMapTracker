/*
 * MapTrackerUIFX.java
 * 
 * Copyright 2024 Subhraman Sarkar <suvrax@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */

package map;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MapTrackerUIFX extends Application {
	private double scaleFactor = 1.0;
	private BufferedImage origBg = null, currentBg = null;
	private Marker currentMarker = null;
	private Vector<Marker> markers = new Vector<>();
	private Vector<Mark> marks = new Vector<>();

	private Scene scene = null;
	private ImageView img = new ImageView();
	private TextField tfCoordX;
	private TextField tfCoordY;

	public static void show(String...args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		stage.setTitle("Map Tracker");
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		// Main Contents: the background image
		img.setOnMouseClicked(e -> {
			addMarker((int) e.getX(), (int) e.getY(), currentMarker);
			updateView();
		});
		img.setOnMouseEntered(e -> updateCursor(currentMarker));
		img.setOnMouseExited(e -> updateCursor(Cursor.DEFAULT));
		ScrollPane scrImg = new ScrollPane();
		scrImg.setContent(img);
		scrImg.setMinSize(size.width/2, 3*size.height/4);
		
		// Bottom Pane
		GridPane pnlForm = new GridPane();
		pnlForm.setHgap(10);
		pnlForm.setVgap(10);
		
		// Row 1
		Label lblMarker = new Label("Markers:");
		ComboBox<Marker> cbMarkers = new ComboBox<>();
		cbMarkers.setOnAction(e -> { currentMarker = cbMarkers.getValue(); });
		Button btnAddMarkers = new Button("Add more markers...");
		btnAddMarkers.setOnAction(e -> updateMarkerList(stage, cbMarkers));
		FlowPane pnlMarkers = new FlowPane(cbMarkers, btnAddMarkers);
		pnlMarkers.setHgap(10);
		pnlForm.add(lblMarker, 0, 0);
		pnlForm.add(pnlMarkers, 1, 0);
		
		// Row 2
		Label lblCoord = new Label("Coordinates:");
		Label lblCoordX = new Label("X:");
		Label lblCoordY = new Label("Y:");
		tfCoordX = new TextField();
		tfCoordY = new TextField();
		FlowPane pnlCoords = new FlowPane(lblCoordX, tfCoordX, lblCoordY, tfCoordY);
		pnlCoords.setHgap(10);
		pnlForm.add(lblCoord, 0, 1);
		pnlForm.add(pnlCoords, 1, 1);
		
		SplitPane mainPane = new SplitPane(scrImg, pnlForm);
		mainPane.setOrientation(Orientation.VERTICAL);

		// Menu
		MenuItem jmiOpen = new MenuItem("Open Background...");
		MenuItem jmiSave = new MenuItem("Save...");
		MenuItem jmiExit = new MenuItem("Exit");
		MenuItem jmiZoomIn = new MenuItem("Zoom In");
		MenuItem jmiZoomOut = new MenuItem("Zoom Out");
		jmiOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		jmiSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		jmiExit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		jmiZoomIn.setAccelerator(KeyCombination.keyCombination("Ctrl+'='"));
		jmiZoomOut.setAccelerator(KeyCombination.keyCombination("Ctrl+'-'"));
		jmiOpen.setOnAction(e -> openImage(stage, img));
		jmiSave.setOnAction(e -> save(stage));
		jmiExit.setOnAction(e -> Platform.exit());
		jmiZoomIn.setOnAction(e -> {
			if (origBg != null) {
				scaleFactor *= 2;
				updateView();
			}
		});
		jmiZoomOut.setOnAction(e -> {
			if (origBg != null) {
				scaleFactor /= 2;
				updateView();
			}
		});
		
		Menu jmFile = new Menu("File", null, jmiOpen, jmiSave, jmiExit);
		Menu jmView = new Menu("View", null, jmiZoomIn, jmiZoomOut); 
		MenuBar bar = new MenuBar(jmFile, jmView);

		scene = new Scene(new VBox(5, bar, mainPane), size.width, size.height);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	private void updateMarkerList(Stage stage, ComboBox<Marker> cbMarkers) {
		Preferences prPref = Preferences.userNodeForPackage(MapTrackerUIFX.class);
		DirectoryChooser files = new DirectoryChooser();
		files.setInitialDirectory(
			new File(prPref.get("lastOpenDirMarkers", System.getProperty("user.home"))));
		File f = files.showDialog(stage);
		if (f == null) {
			return;
		}
		
		try {
			if (f.isDirectory()) {
				System.out.println("Selecting: " + f.getAbsolutePath());
				for (File entry : f.listFiles()) {
					if (!entry.isDirectory()) {
						markers.add(Marker.load(entry.getName(), entry.getAbsolutePath()));
						cbMarkers.getItems().add(markers.lastElement());
						System.out.println("Loaded: " + markers.lastElement());
					}
				}
				cbMarkers.setValue(markers.firstElement());
				prPref.put("lastOpenDirMarkers", f.getParent());
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void openImage(Stage stage, ImageView imgView) {
		Preferences prPref = Preferences.userNodeForPackage(MapTrackerUIFX.class);
		FileChooser files = new FileChooser();
		files.setInitialDirectory(new File(prPref.get("lastOpenDir", System.getProperty("user.home"))));
		File f = files.showOpenDialog(stage);
		if (f == null) {
			return;
		}

		try {
			scaleFactor = 1;
			origBg = ImageIO.read(f);
			imgView.setImage(SwingFXUtils.toFXImage(origBg, null));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			prPref.put("lastOpenDir", f.getParent());
		}
	}

	private void save(Stage stage) {
		Preferences prPref = Preferences.userNodeForPackage(MapTrackerUIFX.class);
		FileChooser files = new FileChooser();
		files.setInitialDirectory(new File(prPref.get("lastSaveDir", System.getProperty("user.home"))));
		File f = files.showSaveDialog(stage);
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			
			PrintStream stream = new PrintStream(f);
			writeCfg(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeCfg(PrintStream stream) throws IOException {
		Properties markerNames = new Properties();
		markerNames.load(getClass().getResourceAsStream("/names.properties"));
		
		for (Mark m : marks) {
			stream.format("{%s %d %d}\n", markerNames.get(m.marker().name()), (int) m.x(), (int) m.y());
		}
	}

	private void addMarker(double x, double y, Marker marker) {
		if (marker == null) return;
		marks.add(new Mark(marker, x, y));
		System.out.println("Drawing marker: " + marks.lastElement());

		tfCoordX.setText("%d".formatted((int) x));
		tfCoordY.setText("%d".formatted((int) y));
	}

	private BufferedImage drawMarkers() {
		BufferedImage overlay = new BufferedImage(
				(int) (origBg.getWidth()),
				(int) (origBg.getHeight()),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = overlay.createGraphics();
		g.drawImage(origBg, 0, 0, overlay.getWidth(), overlay.getHeight(), null);
		
		for (Mark m : marks) {
			System.out.println("Drawing: " + m);
			g.drawImage(m.marker().img(), (int) m.x(), (int) m.y(), null);
		}
		
		g.dispose();
		return overlay;
	}

	private void updateCursor(Marker marker) {
		if (marker == null) return;
		ImageCursor cursor = new ImageCursor(SwingFXUtils.toFXImage(marker.img(), null));
		scene.setCursor(cursor);
	}
	
	private void updateCursor(Cursor inbuiltCursor) {
		scene.setCursor(inbuiltCursor);
	}
	
	private void updateView() {
		currentBg = drawMarkers();
		img.getTransforms().clear();
		img.getTransforms().add(Transform.scale(scaleFactor, scaleFactor));
		img.setImage(SwingFXUtils.toFXImage(currentBg, null));
	}
}
