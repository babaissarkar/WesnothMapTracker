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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import map.*;

public class MapTrackerUIFX extends Application {
	static final Properties markerNames = new Properties();
	
	private Image origBg = null;

	private Marker currentMarker = null;
	private Vector<Marker> markers = new Vector<>();
	private Vector<Mark> marks = new Vector<>();

	private Scene scene = null;
	private TextField tfCoordX = new TextField();
	private TextField tfCoordY = new TextField();
	private TextArea tfPreview = new TextArea();
	
	static {
		try {
			markerNames.load(MapTrackerUIFX.class.getResourceAsStream("/names.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void show(String...args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		stage.setTitle("Map Tracker");	
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();

		// Main Contents: the background image
		Canvas img = new Canvas(screenBounds.getWidth(), screenBounds.getHeight());
		Pane p = new Pane(new Group(img));
		DoubleProperty scaleFactor = new SimpleDoubleProperty(1.0);
		ScrollPane scrImg = new ScrollPane(p);
		
		scaleFactor.addListener(e -> updateView(img, scaleFactor.get()));
		img.setOnMouseClicked(e -> {
			double sf = scaleFactor.get();
			addMarker((int) e.getX(), (int) e.getY(), currentMarker);
			updateView(img, sf);
			p.setPrefWidth(origBg.getWidth() * sf);
			p.setPrefHeight(origBg.getHeight() * sf);
		});
		img.setOnMouseEntered(e -> updateCursor(currentMarker));
		img.setOnMouseExited(e -> updateCursor(Cursor.DEFAULT));
		
		// Bottom Pane
		GridPane pnlForm = new GridPane();
		pnlForm.setHgap(10);
		pnlForm.setVgap(10);
		
		// Row 1
		Label lblMarker = new Label("Markers:");
		lblMarker.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
		
		ComboBox<Marker> cbMarkers = new ComboBox<>();
		cbMarkers.setCellFactory(e -> new MarkerCell());
		cbMarkers.setButtonCell(new MarkerCell());
		loadInternalMarkers(cbMarkers);
		cbMarkers.setOnAction(e -> { currentMarker = cbMarkers.getValue(); });
		
		Button btnAddMarkers = new Button("Load more graphics...");
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
		tfPreview.setText("""
			To start, load a background map image via File > Open Background..., then select a Marker.
			A preview of the generated code will appear here once you start adding markers to the map.
			""");
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
		mainPane.setDividerPosition(0, 0.75);
		mainPane.setOrientation(Orientation.VERTICAL);
		
		pnlForm.setStyle("-fx-background-color: lightblue");

		// Menu
		MenuItem jmiOpen = new MenuItem("Open Background...");
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
		jmiZoomIn.setOnAction(e -> scaleFactor.set(scaleFactor.get() * 2));
		jmiZoomOut.setOnAction(e -> scaleFactor.set(scaleFactor.get() / 2));
		jmiUsage.setOnAction(e -> showHelp());
		
		Menu jmFile = new Menu("File", null, jmiOpen, jmiSave, jmiExit);
		Menu jmView = new Menu("View", null, jmiZoomIn, jmiZoomOut);
		Menu jmHelp = new Menu("Help", null, jmiUsage);
		MenuBar bar = new MenuBar(jmFile, jmView, jmHelp);
		bar.setStyle("-fx-background-color: lightblue");

		VBox box = new VBox(5, bar, mainPane);
		VBox.setVgrow(mainPane, Priority.ALWAYS);
		scene = new Scene(box, screenBounds.getWidth(), screenBounds.getHeight());
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
						markers.add(
							Marker.load(
								(String) markerNames.getProperty(entry.getName()),
								entry.getName(),
								entry));
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

	private void openImage(Stage stage, Canvas imgView) {
		Preferences prPref = Preferences.userNodeForPackage(MapTrackerUIFX.class);
		FileChooser files = new FileChooser();
		files.setInitialDirectory(new File(prPref.get("lastOpenDir", System.getProperty("user.home"))));
		File f = files.showOpenDialog(stage);
		
		if (f == null) return;

		try {
			origBg = SwingFXUtils.toFXImage(ImageIO.read(f), null);
			imgView.widthProperty().bind(origBg.widthProperty());
			imgView.heightProperty().bind(origBg.heightProperty());
			updateView(imgView, 1.0);
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
			if (!f.exists()) f.createNewFile();
			write(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadInternalMarkers(ComboBox<Marker> cbMarkers) {
		markerNames.forEach((k, v) -> {
			try {
				markers.add(Marker.loadInternal(
					(String) v,
					(String) k));
			} catch (IOException e) {
				e.printStackTrace();
			}
			cbMarkers.getItems().add(markers.lastElement());
			System.out.println("Loaded: " + markers.lastElement());
		});
		cbMarkers.setValue(markers.firstElement());
		currentMarker = markers.firstElement();
	}
	
	private void write(PrintWriter stream) {
		for (Mark m : marks) {
			stream.format("{%s %d %d}\n", m.marker().name(), (int) m.x(), (int) m.y());
		}
	}

	private void showHelp() {
		Stage stage = new Stage();
		stage.setTitle("Usage Guide");
		final SwingNode node = new SwingNode();
		SwingUtilities.invokeLater(() -> {
			JTextPane textPane = new JTextPane();
			try {
				textPane.setPage(this.getClass().getResource("/docs/Usage.html"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			node.setContent(new JScrollPane(textPane));
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

	private void updateCursor(Marker marker) {
		if (marker == null) return;
		ImageCursor cursor = new ImageCursor(marker.img());
		scene.setCursor(cursor);
	}
	
	private void updateCursor(Cursor inbuiltCursor) {
		scene.setCursor(inbuiltCursor);
	}
	
	private void updateView(Canvas img, double scaleFactor) {
		GraphicsContext ctx = img.getGraphicsContext2D();
		ctx.clearRect(0, 0, img.getWidth(), img.getHeight());
		System.out.println("Drawing Background");
		ctx.drawImage(origBg, 0, 0);
		for (Mark m : marks) {
			System.out.println("Drawing: " + m);
			ctx.drawImage(m.marker().img(), m.x(), m.y());
		}
		img.getTransforms().clear();
		img.getTransforms().add(Transform.scale(scaleFactor, scaleFactor));
	}
	
	// Custom renderer for the Marker combobox that shows both icons and text
	// default behavior is text only.
	private class MarkerCell extends ListCell<Marker> {
		private ImageView view = new ImageView();
		
		@Override
		public void updateItem(Marker m, boolean empty) {
			super.updateItem(m, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				setText(m.name());
				view.setFitWidth(30);
				view.setFitHeight(30);
				view.setImage(m.img());
				setGraphic(view);
			}
		}
	}
}
