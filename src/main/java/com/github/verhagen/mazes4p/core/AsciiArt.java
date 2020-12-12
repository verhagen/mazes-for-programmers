package com.github.verhagen.mazes4p.core;

public class AsciiArt implements GridVisitor {
	private StringBuilder bldr = new StringBuilder();
	private StringBuilder nextRowTop = new StringBuilder("|");
	private StringBuilder nextRowBottom = new StringBuilder("+");


	public AsciiArt(int columns) {
		bldr.append("+");
		for (int c = 0; c < columns; c++) {
			bldr.append("---+");
		}
		bldr.append("\n");
	}


	@Override
	public void begin() {
		// No implementation needed
	}

	@Override
	public void nextRow() {
		bldr.append(nextRowTop).append("\n");
		bldr.append(nextRowBottom).append("\n");
		nextRowTop = new StringBuilder("|");
		nextRowBottom = new StringBuilder("+");
	}

	@Override
	public void visit(Cell cell) {
		nextRowTop.append("   ").append(cell.isLinked(Direction.EAST) ? " " : "|");
		nextRowBottom.append(cell.isLinked(Direction.SOUTH) ? "   " : "---").append("+");
	}

	@Override
	public void end() {
		bldr.append(nextRowTop).append("\n");
		bldr.append(nextRowBottom).append("\n");
	}

	@Override
	public String toString() {
		return bldr.toString();
	}

}
