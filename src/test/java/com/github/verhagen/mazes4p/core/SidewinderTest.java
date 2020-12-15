package com.github.verhagen.mazes4p.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.github.verhagen.mazes4p.core.creator.Sidewinder;
import com.github.verhagen.mazes4p.output.AsciiArt;
import com.github.verhagen.mazes4p.output.Graphics2DArt;
import com.github.verhagen.mazes4p.output.Graphics2DArt2WithDistance;
import com.github.verhagen.mazes4p.output.Graphics2DHtml;
import com.github.verhagen.mazes4p.output.Graphics2DHtmlWithDistance;

public class SidewinderTest {
	private Path imagePath = Paths.get("target", "image");
	private Path htmlPath = Paths.get("target", "html");
	private Random random = new Random();


	@ParameterizedTest
	@CsvSource({ "25, 25, 3731109034316917236" }) //, "100, 100"  "4, 4", "6, 6", "12, 12",
	public void grid(int rows, int columns, long seed) {
		Summary summary = new Summary();

		Grid grid = new Grid(rows, columns, (seed == 0) ? random.nextLong() : seed);
		summary.setGrid(grid);
		summary.setMazeCreator(new Sidewinder());
		summary.getMazeCreator().on(grid);
		AsciiArt visitor = new AsciiArt(columns, grid.getSeed());
		grid.accept(visitor);
		System.out.println(visitor.toString());

		Distances midGridDistances = grid.get(rows / 2, columns / 2).distances();
		
		int colorIndex = random.nextInt(6);
		Graphics2DArt graphicsVisitor = new Graphics2DArt2WithDistance(imagePath, "sidewinder", rows, columns, summary
				, 10, midGridDistances, colorIndex);
		grid.accept(graphicsVisitor);

		Graphics2DHtml graphics2dHtml = new Graphics2DHtmlWithDistance(htmlPath, "sidewinder", rows, columns, summary
				, 10, midGridDistances, colorIndex);
		grid.accept(graphics2dHtml);
	}

}
