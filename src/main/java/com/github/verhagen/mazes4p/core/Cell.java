package com.github.verhagen.mazes4p.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cell {
	public enum ToStringType {
			NORMAL
			, WITH_NEIGHBORS
			};
	private int row;
	private int column;
	private Map<Direction, Cell> neighbors;
	private Map<Cell, Boolean> links;
	
	public Cell(int row, int column) {
		this.row = row;
		this.column = column;
		this.neighbors = new HashMap<>();
		this.links = new HashMap<>();
	}
	
	public Cell link(Collection<Cell> cells) {
		for (Cell cellToLink : cells) {
			link(cellToLink);
		}
		return this;
	}
	public Cell link(Cell cellToLink) {
		links.put(cellToLink, true);
		cellToLink.links.put(this, true);
		return this;
	}
	public Cell unlink(Cell linkedCell) {
		linkedCell.links.remove(this);
		links.remove(linkedCell);
		return this;
	}
	public boolean isLinked(Direction direction) {
		if (! neighbors.containsKey(direction)) {
			return false;
		}
		Cell cell = neighbors.get(direction);
		return links.containsKey(cell);
	}
	
	public Set<?> getLinks() {
		return links.keySet();
	}
	public boolean isLinked(Cell cell) {
		return links.containsKey(cell);
	}
	
	public void put(Direction direction, Cell cell) {
		if (cell == null) {
			return;
		}
		neighbors.put(direction, cell);
	}
	public Collection<Cell> getNeighbors() {
		return neighbors.values();
	}

//	public Cell getNeighbor(Direction direction) {
//		if (neighbors.containsKey(direction)) {
//			return neighbors.get(direction);
//		}
//		return null;
//	}
	public List<Cell> getNeighbors(Direction... dirs) {
		List<Cell> list = new ArrayList<>();
		for (Direction direction : dirs) {
			if (neighbors.containsKey(direction)) {
				list.add(neighbors.get(direction));
			}
		}
		return list;
	}

	@Override
	public String toString() {
		return toString(ToStringType.NORMAL);
	}
	public String toString(ToStringType type) {
		switch (type) {
		case NORMAL:
			return "(" + row +  ", " + column + ")";
		case WITH_NEIGHBORS:
			return "(" + row +  ", " + column + ")" + neighbors.keySet();
		}
		return "";
	}

	public Direction getNeighborDirection(Cell neighbor) {
		if (! neighbors.containsValue(neighbor)) {
			throw new IllegalArgumentException("Argument 'neightbor' with value " + neighbor 
					+ " is not a neightbor of cell " + this);
		}

		for (Entry<Direction, Cell> entry : neighbors.entrySet()) {
			if (entry.getValue() == neighbor) {
				return entry.getKey();
			}
		}
		return null;
	}

	public boolean isNeighborAvailable(Direction direction) {
		return neighbors.containsKey(direction);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
