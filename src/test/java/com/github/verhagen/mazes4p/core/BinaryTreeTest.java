package com.github.verhagen.mazes4p.core;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BinaryTreeTest {
	private Path imagePath = Paths.get("target", "image");


	@ParameterizedTest
	@CsvSource({ "4, 4", "6, 6", "12, 12" })
	public void grid(int rows, int columns) {
		Grid grid = new Grid(rows, columns);
		new BinaryTree().on(grid);
//		AsciiArt visitor = new AsciiArt(columns);
		Distances distances = grid.get(0, 0).distances();
		AsciiArt visitor = new AsciiArtWithDistances(columns, distances);
		grid.accept(visitor);
		System.out.println(visitor.toString());

		Cell newStart = distances.max();
		Distances newDistances = newStart.distances();
		Distances breadcrumbs = newDistances.pathTo(newDistances.max());
		System.out.println("new start " + newStart + "  max " + newDistances.max());
		visitor = new AsciiArtWithDistances(columns, breadcrumbs);
		grid.accept(visitor);
		System.out.println(visitor.toString());

		Graphics2DArt graphicsVisitor = new Graphics2DArt2WithDistance(imagePath, "binary-tree", rows, columns, 10, distances);
		grid.accept(graphicsVisitor);
	}

}
