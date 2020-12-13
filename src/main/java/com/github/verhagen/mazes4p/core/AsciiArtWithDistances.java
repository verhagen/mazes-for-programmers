package com.github.verhagen.mazes4p.core;

public class AsciiArtWithDistances extends AsciiArt {
	private Distances distances;


	public AsciiArtWithDistances(int columns, Distances distances) {
		super(columns);
		this.distances = distances;
	}


	@Override
	public String content(Cell cell) {
		int distance = distances.getDistanceToRoot(cell);
		return (distance == -1 ? "  " : (distance < 10 ? " " : "") + String.valueOf(distance));
	}
	
}
