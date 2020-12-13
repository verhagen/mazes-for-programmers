package com.github.verhagen.mazes4p.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Graphics2DArt implements GridVisitor {
	protected static Logger logger = LoggerFactory.getLogger(Graphics2DArt.class);
	private BufferedImage bi;
	protected Graphics2D ig2;
	private int cellSize;
	private Color WALL = Color.BLACK;
	private Path imagePath;
	private String name;

	public Graphics2DArt(Path imagePath, String generatorName, int rows, int columns, int cellSize) {
		this.imagePath = imagePath;
		this.cellSize = cellSize;
		bi = new BufferedImage(columns * cellSize + 1, rows * cellSize + 1, BufferedImage.TYPE_INT_ARGB);
		ig2 = bi.createGraphics();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		name = MessageFormat.format("{0}-maze-{1}x{2}-{3}."
				, LocalDateTime.now().format(dtf), generatorName, rows, columns);

	}

	@Override
	public void nextRow() {
		// No implementation needed
	}

	@Override
	public void visit(Cell cell) {
		int x1 = cell.getColumn() * cellSize;
		int y1 = cell.getRow() * cellSize;
		int x2 = (cell.getColumn() + 1) * cellSize;
		int y2 = (cell.getRow() + 1) * cellSize;
		draw(cell, x1, y1, x2, y2);

	}

	protected void draw(Cell cell, int x1, int y1, int x2, int y2) {
		if (! cell.isLinked(Direction.NORTH)) {
			drawLine(x1, y1, x2, y1, WALL);
		}
		if (! cell.isLinked(Direction.WEST)) {
			drawLine(x1, y1, x1, y2, WALL);
		}

		if (! cell.isLinked(Direction.EAST)) {
			drawLine(x2, y1, x2, y2, WALL);
		}
		if (! cell.isLinked(Direction.SOUTH)) {
			drawLine(x1, y2, x2, y2, WALL);
		}
	}

	private void drawLine(int x1, int y1, int x2, int y2, Color color) {
		ig2.setColor(color);
		ig2.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public void begin() {
		// No implementation needed
	}

	@Override
	public void end() {
		String[] types = new String[] { "png" };
		for (String type : types) {
			Path filePath = imagePath.resolve(name + type);
			try {
				
				ImageIO.write(bi, type, filePath.toFile());
			}
			catch (IOException ioe) {
				logger.warn("Unable to write ", ioe);
			}
		}
	}

}
