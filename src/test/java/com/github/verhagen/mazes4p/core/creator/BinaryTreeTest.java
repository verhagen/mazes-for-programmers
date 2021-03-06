package com.github.verhagen.mazes4p.core.creator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Distances;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.Summary;
import com.github.verhagen.mazes4p.core.creator.BinaryTree;
import com.github.verhagen.mazes4p.output.AsciiArt;
import com.github.verhagen.mazes4p.output.AsciiArtWithDistances;
import com.github.verhagen.mazes4p.output.Graphics2DArt;
import com.github.verhagen.mazes4p.output.Graphics2DArt2WithDistance;
import com.github.verhagen.mazes4p.output.Graphics2DHtml;
import com.github.verhagen.mazes4p.output.Graphics2DHtmlWithDistance;

public class BinaryTreeTest {
	private Path imagePath = Paths.get("target", "image");
	private Path htmlPath = Paths.get("target", "html");
	private Random random = new Random();


	@ParameterizedTest
	@CsvSource({ "25, 25"}) //"4, 4", "6, 6", "12, 12", "100, 100"
	public void grid(int rows, int columns) {
		Summary summary = new Summary();

		Grid grid = new Grid(rows, columns, random.nextLong());
		summary.setGrid(grid);
		summary.setMazeCreator(new BinaryTree());
		summary.getMazeCreator().on(grid);
//		AsciiArt visitor = new AsciiArt(columns);
		Distances distances = grid.get(0, 0).distances();
		AsciiArt visitor = new AsciiArtWithDistances(columns, grid.getSeed(), distances);
		grid.accept(visitor);
		System.out.println(visitor.toString());

		Cell newStart = distances.max();
		Distances newDistances = newStart.distances();
		Distances breadcrumbs = newDistances.pathTo(newDistances.max());
		System.out.println("new start " + newStart + "  max " + newDistances.max());
		visitor = new AsciiArtWithDistances(columns, grid.getSeed(), breadcrumbs);
		grid.accept(visitor);
		System.out.println(visitor.toString());

		Distances midGridDistances = grid.get(rows / 2, columns / 2).distances();
		int colorIndex = random.nextInt(6);
		Graphics2DArt graphicsVisitor = new Graphics2DArt2WithDistance(imagePath, "binary-tree", rows, columns, summary
				, 10, midGridDistances, colorIndex);
		grid.accept(graphicsVisitor);

		Graphics2DHtml graphics2dHtml = new Graphics2DHtmlWithDistance(htmlPath, "binary-tree", rows, columns, summary
				, 10, midGridDistances, colorIndex);
		grid.accept(graphics2dHtml);
	}

}
