package com.github.verhagen.mazes4p.output;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Direction;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.GridVisitor;
import com.github.verhagen.mazes4p.core.Summary;

public class Graphics2DHtml implements GridVisitor {
	protected static Logger logger = LoggerFactory.getLogger(Graphics2DHtml.class);
	private int cellSize;
	private Color WALL = Color.RED;
	private Path targetPath;
	private String name;
	private Summary summary;
	protected StringBuilder codeBlock;

	
	public Graphics2DHtml(Path targetPath, String generatorName, int rows, int columns, Summary summary, int cellSize) {
		this.targetPath = targetPath;
		this.cellSize = cellSize;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		name = MessageFormat.format("{0}-maze-{1}-{2}x{3}."
				, LocalDateTime.now().format(dtf), generatorName, rows, columns);
		this.summary = summary;
		this.summary.setFiles(new ArrayList<>());
		this.codeBlock = new StringBuilder();
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
		add(codeBlock, "ctx.fillStyle = '#" + toHex(color) + "';");
		add(codeBlock, "ctx.moveTo(" + x1 + ", " + y1 + ");");
		add(codeBlock, "ctx.lineTo(" + x2 + ", " + y2 + ");");
		add(codeBlock, "ctx.stroke();");
	}

//	private void add(StringBuilder codeBlock, String codeLine, Object... arguments) {
//		if (arguments.length > 0) {
//			add(codeBlock, MessageFormat.format(codeLine, arguments));
//		}
//	}

	protected String toHex(Color color) {
		return toHex(color.getRed())
				+ toHex(color.getGreen())
				+ toHex(color.getBlue());
	}

	private String toHex(int value) {
		String hexString = Integer.toHexString(value).toUpperCase();
		return (hexString.length() < 2) ? "0" + hexString : hexString;
	}

	protected void add(StringBuilder codeBlock, String codeLine) {
		if (codeBlock.length() > 0) {
			codeBlock.append("\n");
		}
		codeBlock.append(codeLine);
	}

	@Override
	public void begin(Grid grid) {
		add(codeBlock, "var canvas = document.getElementById('maze-canvas');");
		add(codeBlock, "var ctx = canvas.getContext('2d');");
	}

	@Override
	public void end(Grid grid) {
		if (! targetPath.toFile().exists()) {
			targetPath.toFile().mkdirs();
		}
		String[] types = new String[] { "js" };
		for (String type : types) {
			Path filePath = targetPath.resolve(name + type);
			try (FileWriter writer = new FileWriter(filePath.toFile())) {
				writer.append(codeBlock);
			}
			catch (IOException ioe) {
				logger.warn("Unable to create / write '" + filePath + "'", ioe);
			}
			summary.addFile(filePath.getFileName());
		}
		createSummary(targetPath, summary);
		createSummaryHtml(targetPath, summary);
	}

	private void createSummary(Path targetPath, Summary summary) {
		Path filePath = targetPath.resolve(name + "json");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(filePath.toFile(), summary);
		}
		catch (IOException ioe) {
			logger.error("Unable to create / write file '" + filePath + "'", ioe);
		}
	}

	private void createSummaryHtml(Path targetPath, Summary summary) {
		Path filePath = targetPath.resolve(name + "html");
		StringBuilder codeBlock = new StringBuilder();
		add(codeBlock, "<!DOCTYPE html>");
		add(codeBlock, "<html>");
		add(codeBlock, "<body>");
		add(codeBlock, "");
		add(codeBlock, "<canvas id='maze-canvas' width='251' height='251'");
		add(codeBlock, "style='border:0px solid #c3c3c3;'>");
		add(codeBlock, "Your browser does not support the canvas element.");
		add(codeBlock, "</canvas>");
		add(codeBlock, "");
		add(codeBlock, "<script src='" + summary.getFiles().get(0) + "'></script>");
		add(codeBlock, "");
//		add(codeBlock, "<img  src='../image/20201214-220501-maze-binary-tree-25x25.png' />");
		add(codeBlock, "</body>");
		add(codeBlock, "</html>");
		try (FileWriter writer = new FileWriter(filePath.toFile())) {
			writer.append(codeBlock);
		}
		catch (IOException ioe) {
			logger.error("Unable to create / write file '" + filePath + "'", ioe);
		}
	}

}
