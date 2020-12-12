package com.github.verhagen.mazes4p.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SidewinderTest {

	@ParameterizedTest
	@CsvSource({ "4, 4", "6, 6", "12, 12" })
	public void grid(int rows, int columns) {
		Grid grid = new Grid(rows, columns);
		new Sidewinder().on(grid);
		AsciiArt visitor = new AsciiArt(columns);
		grid.accept(visitor);
		System.out.println(visitor.toString());
	}

}
