/*
 * MapTrackerUIFX.java
 * 
 * Copyright 2024-2025 Subhraman Sarkar <suvrax@gmail.com>
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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import map.*;

public class MapTrackerUIFX extends Application {
	private double scaleFactor = 1.0;
	private BufferedImage origBg = null, currentBg = null;
	private Marker currentMarker = null;
	private Vector<Marker> markers = new Vector<>();
	private Vector<Mark> marks = new Vector<>();

	private Scene scene = null;
	private TextField tfCoordX = new TextField();
	private TextField tfCoordY = new TextField();
	private TextArea tfPreview = new TextArea();

	public static void show(String...args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		stage.setTitle("Map Tracker");
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		// Main Contents: the background image
		ImageView img = new ImageView();
		img.setOnMouseClicked(e -> {
			addMarker((int) e.getX(), (int) e.getY(), currentMarker);
			updateView(img);
		});
		img.setOnMouseEntered(e -> updateCursor(currentMarker));
		img.setOnMouseExited(e -> updateCursor(Cursor.DEFAULT));
		
		ScrollPane scrImg = new ScrollPane();
		scrImg.setContent(img);
		scrImg.setMinSize(0.5*size.width, 0.70*size.height);
		
		// Bottom Pane
		GridPane pnlForm = new GridPane();
		pnlForm.setHgap(10);
		pnlForm.setVgap(10);
		
		// Row 1
		Label lblMarker = new Label("Markers:");
		lblMarker.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
		
		ComboBox<Marker> cbMarkers = new ComboBox<>();
		cbMarkers.setOnAction(e -> { currentMarker = cbMarkers.getValue(); });
		
		Button btnAddMarkers = new Button("Lore more graphics...");
		btnAddMarkers.setOnAction(e -> updateMarkerList(stage, cbMarkers));
		
		FlowPane pnlMarkers = new FlowPane(cbMarkers, btnAddMarkers);
		pnlMarkers.setHgap(10);
		pnlMarkers.setAlignment(Pos.CENTER_LEFT);
		pnlForm.add(lblMarker, 0, 0);
		pnlForm.add(pnlMarkers, 0, 1);
		
		// Row 2
		Label lblCoord  = new Label("Coordinates:");
		lblCoord.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
		Label lblCoordX = new Label("X");
		lblCoordX.setStyle("-fx-font-size: 14");
		Label lblCoordY = new Label("Y");
		lblCoordY.setStyle("-fx-font-size: 14");
		
		FlowPane pnlCoords = new FlowPane(lblCoordX, tfCoordX, lblCoordY, tfCoordY);
		pnlCoords.setHgap(10);
		pnlCoords.setAlignment(Pos.CENTER_LEFT);
		pnlForm.add(lblCoord, 0, 2);
		pnlForm.add(pnlCoords, 0, 3);
		
		// Output Preview TextField (row 1 to 4, col 2)
		tfPreview.setText("A preview of the generated code will appear here once you start adding markers to the map...");
		tfPreview.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		pnlForm.add(tfPreview, 1, 0, 4, 4);
		
		// Constraints so the GridPane grows correctly
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setHgrow(Priority.NEVER);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setHgrow(Priority.ALWAYS);
		pnlForm.getColumnConstraints().addAll(col1, col1, col2);
		RowConstraints row1 = new RowConstraints();
		row1.setVgrow(Priority.ALWAYS);
		pnlForm.getRowConstraints().addAll(row1, row1, row1, row1);
		pnlForm.setPadding(new Insets(10));
		
		SplitPane mainPane = new SplitPane(scrImg, pnlForm);
		mainPane.setOrientation(Orientation.VERTICAL);
		
		pnlForm.setStyle("-fx-background-color: lightblue");

		// Menu
		MenuItem jmiOpen = new MenuItem("Open Background Map Image...");
		MenuItem jmiSave = new MenuItem("Save...");
		MenuItem jmiExit = new MenuItem("Exit");
		MenuItem jmiZoomIn = new MenuItem("Zoom In");
		MenuItem jmiZoomOut = new MenuItem("Zoom Out");
		MenuItem jmiUsage = new MenuItem("Usage Guide");
		
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
				scaleFactor /= 2;
				updateView(img);
			}
		});
		jmiZoomOut.setOnAction(e -> {
			if (origBg != null) {
				scaleFactor *= 2;
				updateView(img);
			}
		});
		jmiUsage.setOnAction(e -> showHelp());
		
		Menu jmFile = new Menu("File", null, jmiOpen, jmiSave, jmiExit);
		Menu jmView = new Menu("View", null, jmiZoomIn, jmiZoomOut);
		Menu jmHelp = new Menu("Help", null, jmiUsage);
		MenuBar bar = new MenuBar(jmFile, jmView, jmHelp);
		bar.setStyle("-fx-background-color: lightblue");

		VBox box = new VBox(5, bar, mainPane);
		VBox.setVgrow(mainPane, Priority.ALWAYS);
		scene = new Scene(box, size.width, size.height);
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
		
		if (f == null) return;
		
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
		
		if (f == null) return;

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
		try (PrintWriter stream = new PrintWriter(f)) {
			if (!f.exists()) {
				f.createNewFile();
			}
			
			write(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void write(PrintWriter stream) {
		Properties markerNames = new Properties();
		try {
			markerNames.load(getClass().getResourceAsStream("/names.properties"));
			for (Mark m : marks) {
				stream.format("{%s %d %d}\n", markerNames.get(m.marker().name()), (int) m.x(), (int) m.y());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showHelp() {
		Stage stage = new Stage();
		stage.setTitle("Usage Guide");
		final SwingNode node = new SwingNode();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JTextPane textPane = new JTextPane();
				try {
					textPane.setPage(this.getClass().getResource("/docs/Usage.html"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				node.setContent(new JScrollPane(textPane));
			}
		});
		VBox box = new VBox(node);
		VBox.setVgrow(node, Priority.ALWAYS);
		Scene scene = new Scene(box, 640, 480);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	private void addMarker(double x, double y, Marker marker) {
		if (marker == null) return;
		
		marks.add(new Mark(marker, x, y));
		StringWriter writer = new StringWriter();
		write(new PrintWriter(writer));
		tfPreview.setText(writer.toString());
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
	
	private void updateView(ImageView img) {
		currentBg = drawMarkers();
		img.getTransforms().clear();
		img.getTransforms().add(Transform.scale(scaleFactor, scaleFactor));
		img.setImage(SwingFXUtils.toFXImage(currentBg, null));
	}
}
