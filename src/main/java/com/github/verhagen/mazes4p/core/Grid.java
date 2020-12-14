package com.github.verhagen.mazes4p.core;


public class Grid {
	private int rows;
	private int columns;
	private Cell[][] matrix;
	private long seed;


	public Grid(int rows, int columns, long seed) {
		this.rows = rows;
		this.columns = columns;
		this.seed = seed;
		prepareGrid();
		configureCells();
	}

	public void prepareGrid() {
		matrix = new Cell[rows][];
		for (int r = 0; r < rows; r++) {
			matrix[r] = new Cell[columns];
			for (int c = 0; c < columns; c++) {
				matrix[r][c] = new Cell(r, c);
			}
		}
	}

	public void configureCells() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Cell cell = matrix[r][c];
				cell.put(Direction.NORTH, get(r - 1, c));
				cell.put(Direction.SOUTH, get(r + 1, c));
				cell.put(Direction.WEST, get(r, c - 1));
				cell.put(Direction.EAST, get(r, c + 1));
			}
		}
	}
	
	public Cell get(int row, int column) {
		if (row < 0 || row >= rows || column < 0 || column >= columns) {
			return null;
		}
		return matrix[row][column];
	}
	
	public Cell getRandom() {
		return null;
	}
	
	public int size() {
		return rows * columns;
	}

	public long getSeed() {
		return seed;
	}

	public void accept(CellVisitor visitor) {
		for (int r = 0; r < rows; r++) {
			if (r > 0) {
				visitor.nextRow();
			}
			for (int c = 0; c < columns; c++) {
				Cell cell = matrix[r][c];
				visitor.visit(cell);
			}
		}
	}

	public void accept(GridVisitor visitor) {
		visitor.begin();
		accept((CellVisitor)visitor);
		visitor.end();
	}
}
