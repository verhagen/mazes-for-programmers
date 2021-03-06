package com.github.verhagen.mazes4p.output;

import java.awt.Color;
import java.nio.file.Path;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Distances;
import com.github.verhagen.mazes4p.core.Summary;

public class Graphics2DHtmlWithDistance extends Graphics2DHtml {
	private int colorIndex;
	private Distances distances;
	private int maxDistance;


	public Graphics2DHtmlWithDistance(Path imagePath, String generatorName, int rows, int columns, Summary summary, int cellSize
			, Distances distances, int colorIndex) {
		super(imagePath, generatorName, rows, columns, summary, cellSize);
		this.colorIndex = colorIndex;
		this.distances = distances;
		maxDistance = distances.getDistanceToRoot(distances.max());
	}


	@Override
	protected void draw(Cell cell, int x1, int y1, int x2, int y2) {
		Color color = background(cell);
		if (color == null) {
			return;
		}
		add(codeBlock, "ctx.fillStyle = '#" + toHex(color) + "';");
		add(codeBlock, "ctx.fillRect(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ");");

		super.draw(cell, x1, y1, x2, y2);
	}

	private Color background(Cell cell) {
		if (! distances.isKnown(cell)) {
			return null;
		}
		logger.info("max dis: " + maxDistance + "  dis: " + distances.getDistanceToRoot(cell));
		float intensity = Float.valueOf(maxDistance - distances.getDistanceToRoot(cell)) / maxDistance;
		logger.info("cell: " + cell + "  intensity: " + intensity);
		int dark = Math.round(255 * intensity);
		int bright = Math.round(128 + (127 * intensity));
		logger.info("dark: " + dark + "  bright: " + bright);
		switch (colorIndex) {
			case 0: return new Color(bright, dark, dark); 
			case 1: return new Color(dark, bright, dark);
			case 2: return new Color(dark, dark, bright);
			case 3: return new Color(bright, bright, dark);
			case 4: return new Color(bright, dark, bright);
			case 5: return new Color(dark, bright, bright);
		}
		return null;
	}

}
