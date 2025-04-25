/*
 * Marker.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

// Note: JavaFX doesn't have webp support, so we need to piggyback ImageIO
// to use it's webp-imageio support bridge.

public record Marker(String name, String filename, Image img) {
	
	public static Marker load(String name, String filename, File imgFile) throws IOException {
		var img = ImageIO.read(new FileInputStream(imgFile));
		return new Marker(name, filename, SwingFXUtils.toFXImage(img, null));
	}
	
	public static Marker loadInternal(String name, String filename) throws IOException {
		var img = ImageIO.read(Marker.class.getResourceAsStream("/markers/" + filename));
		return new Marker(name, filename, SwingFXUtils.toFXImage(img, null));
	}
	
	@Override
	public String toString() {
		return name();
	}
}
