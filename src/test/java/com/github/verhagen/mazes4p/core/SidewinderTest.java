package com.github.verhagen.mazes4p.core;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SidewinderTest {
	private Path imagePath = Paths.get("target", "image");


	@ParameterizedTest
	@CsvSource({ "4, 4", "6, 6", "12, 12" })
	public void grid(int rows, int columns) {
		Grid grid = new Grid(rows, columns);
		new Sidewinder().on(grid);
		AsciiArt visitor = new AsciiArt(columns);
		grid.accept(visitor);
		System.out.println(visitor.toString());

		Graphics2DArt graphicsVisitor = new Graphics2DArt(imagePath, "sidewinder", rows, columns, 10);
		grid.accept(graphicsVisitor);
	}

}
