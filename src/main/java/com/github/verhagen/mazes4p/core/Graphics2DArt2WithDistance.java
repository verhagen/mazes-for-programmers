package com.github.verhagen.mazes4p.core;

import java.awt.Color;
import java.nio.file.Path;

public class Graphics2DArt2WithDistance extends Graphics2DArt {
	private Distances distances;
	private int maxDistance;


	public Graphics2DArt2WithDistance(Path imagePath, String generatorName, int rows, int columns, int cellSize, Distances distances) {
		super(imagePath, generatorName, rows, columns, cellSize);
		this.distances = distances;
		maxDistance = distances.getDistanceToRoot(distances.max());
	}

	@Override
	protected void draw(Cell cell, int x1, int y1, int x2, int y2) {
		Color color = background(cell);
		if (color == null) {
			return;
		}
		ig2.setColor(color);
		ig2.fillRect(x1, y1, x2 - x1, y2 - y1);

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
		return new Color(bright, dark, bright);
	}

}
