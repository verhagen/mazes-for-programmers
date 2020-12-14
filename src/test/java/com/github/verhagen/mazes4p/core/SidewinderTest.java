package com.github.verhagen.mazes4p.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.github.verhagen.mazes4p.core.algorithm.Sidewinder;

public class SidewinderTest {
	private Path imagePath = Paths.get("target", "image");
	private Random random = new Random();


	@ParameterizedTest
	@CsvSource({  "25, 25" }) //, "100, 100"  "4, 4", "6, 6", "12, 12",
	public void grid(int rows, int columns) {
		Grid grid = new Grid(rows, columns, random.nextLong());
		new Sidewinder().on(grid);
		AsciiArt visitor = new AsciiArt(columns);
		grid.accept(visitor);
		System.out.println(visitor.toString());

		Distances distances = grid.get(rows / 2, columns / 2).distances();
		
		Graphics2DArt graphicsVisitor = new Graphics2DArt2WithDistance(imagePath, "sidewinder", rows, columns, 10
				, distances, random.nextInt(6));
		grid.accept(graphicsVisitor);
	}

}
