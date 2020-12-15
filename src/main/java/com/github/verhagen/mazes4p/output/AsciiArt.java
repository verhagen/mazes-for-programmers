package com.github.verhagen.mazes4p.output;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Direction;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.GridVisitor;

public class AsciiArt implements GridVisitor {
	private StringBuilder bldr = new StringBuilder();
	private StringBuilder nextRowTop = new StringBuilder("|");
	private StringBuilder nextRowBottom = new StringBuilder("+");


	public AsciiArt(int columns, long seed) {
	}


	@Override
	public void begin(Grid grid) {
		bldr.append("Maze (seed: " + grid.getSeed() + ";  death-ends: " + grid.getDeathEnds() + ")\n");
		bldr.append("+");
		for (int c = 0; c < grid.getColumns(); c++) {
			bldr.append("----+");
		}
		bldr.append("\n");
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
		nextRowTop.append(" ").append(content(cell)).append(" ").append(cell.isLinked(Direction.EAST) ? " " : "|");
		nextRowBottom.append(cell.isLinked(Direction.SOUTH) ? "    " : "----").append("+");
	}

	public String content(Cell cell) {
		return "  ";
	}


	@Override
	public void end(Grid grid) {
		bldr.append(nextRowTop).append("\n");
		bldr.append(nextRowBottom).append("\n");
	}

	@Override
	public String toString() {
		return bldr.toString();
	}

}
