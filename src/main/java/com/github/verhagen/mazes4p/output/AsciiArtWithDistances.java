package com.github.verhagen.mazes4p.output;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Distances;

public class AsciiArtWithDistances extends AsciiArt {
	private Distances distances;


	public AsciiArtWithDistances(int columns, long seed, Distances distances) {
		super(columns, seed);
		this.distances = distances;
	}


	@Override
	public String content(Cell cell) {
		int distance = distances.getDistanceToRoot(cell);
		return (distance == -1 ? "  " : (distance < 10 ? " " : "") + String.valueOf(distance));
	}
	
}
