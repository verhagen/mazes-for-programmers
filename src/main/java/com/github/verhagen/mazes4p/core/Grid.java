package com.github.verhagen.mazes4p.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Grid {
	private int rows;
	private int columns;
	private Cell[][] matrix;
	private long seed;
	@JsonIgnore
	private Random random;


	public Grid(int rows, int columns, long seed) {
		this.rows = rows;
		this.columns = columns;
		this.seed = seed;
		this.random = new Random(seed);
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
	
	@JsonIgnore
	public Random getRandom() {
		return random;
	}

	@JsonIgnore
	public Cell getRandomCell() {
		return get(random.nextInt(rows), random.nextInt(columns));
	}

	public int size() {
		return rows * columns;
	}

	public long getSeed() {
		return seed;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	
	public int getDeathEndsCounted() {
		return getDeathEnds().size();
	}

	@JsonIgnore
	public Collection<Cell> getDeathEnds() {
		Set<Cell> deathEnds = new HashSet<>();
		accept(new CellVisitor() {
			@Override
			public void visit(Cell cell) {
				if (cell.getLinks().size() == 1) {
					deathEnds.add(cell);
				}
			}
			
			@Override
			public void nextRow() {
				// No implementation needed
			}
		});
		return deathEnds;
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
		visitor.begin(this);
		accept((CellVisitor)visitor);
		visitor.end(this);
	}

}
