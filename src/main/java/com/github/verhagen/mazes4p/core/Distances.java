package com.github.verhagen.mazes4p.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Distances {
	private Logger logger = LoggerFactory.getLogger(Distances.class);
	private Map<Cell, Integer> cells = new HashMap<>();
	private Cell root;

//	public Distances(Cell root) {
//		this.root = root;
//		cells.put(root, Integer.valueOf(0));
//	}

	public void add(Cell cell, int distance) {
		if (isKnown(cell)) {
			throw new IllegalArgumentException("Argument 'cell' with calue '" + cell + "' should only be added once."
					+ " This cell is already added with a distance '" + cells.get(cell) + "' from the starting point.");
		}
		if (distance == 0) {
			root = cell;
		}
		cells.put(cell, Integer.valueOf(distance));
	}
	
	public boolean isKnown(Cell cell) {
		return cells.containsKey(cell);
	}
	
	public int getDistanceToRoot(Cell cell) {
		if (! cells.containsKey(cell)) {
			return -1;
		}
		return cells.get(cell).intValue();
	}

	public Set<Cell> getCells() {
		return cells.keySet();
	}

	public Distances pathTo(Cell goal) {
		Cell current = goal;
		Distances breadcrumbs = new Distances();
		breadcrumbs.add(current, cells.get(current));
		while (current != root) {
			logger.info("Current: " + current);
			for (Cell neighbor : current.getLinks()) {
				if (cells.get(neighbor) < cells.get(current)) {
					logger.info("cells.get(" + neighbor + ") < cells.get(" + current + ")");
					breadcrumbs.add(neighbor, cells.get(neighbor));
					current = neighbor;
					logger.info("New Current: " + current);
					continue;
				}
			}
		}
		return breadcrumbs;
	}

	public Cell max() {
		int maxDistance = 0;
		Cell maxCell = root;
		for (Entry<Cell, Integer> entry : cells.entrySet()) {
			if (maxDistance < entry.getValue()) {
				maxDistance = entry.getValue();
				maxCell = entry.getKey();
			}
		}
		return maxCell;
	}
}
