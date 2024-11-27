package map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public record Marker(String name, String path, BufferedImage img) {
	
	public static Marker load(String name, String path) throws IOException {
		var img = ImageIO.read(new FileInputStream(new File(path)));
		return new Marker(name, path, img);
	}
	
	@Override
	public String toString() {
		return name();
	}
}
