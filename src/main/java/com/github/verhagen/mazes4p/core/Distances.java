package com.github.verhagen.mazes4p.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Distances {
	private Map<Cell, Integer> cells = new HashMap<>();

	public Distances(Cell root) {
		cells.put(root, Integer.valueOf(0));
	}

	public void add(Cell cell, int distance) {
		if (isKnown(cell)) {
			throw new IllegalArgumentException("Argument 'cell' with calue '" + cell + "' should only be added once."
					+ " This cell is already added with a distance '" + cells.get(cell) + "' from the starting point.");
		}
		cells.put(cell, Integer.valueOf(distance));
	}
	
	public boolean isKnown(Cell cell) {
		return cells.containsKey(cell);
	}
	
	public int getDistanceToRoot(Cell cell) {
		return cells.get(cell).intValue();
	}

	public Set<Cell> getCells() {
		return cells.keySet();
	}
	
}
