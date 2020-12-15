package com.github.verhagen.mazes4p.core.creator;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.CellVisitor;
import com.github.verhagen.mazes4p.core.Direction;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.MazeCreator;

public class BinaryTree implements MazeCreator {
	private static Logger logger = LoggerFactory.getLogger(BinaryTree.class);


	@Override
	public void on(Grid grid) {
		grid.accept(new CellVisitor() {
			private Random random = new Random(grid.getSeed());
			private Direction[] dirs = new Direction[] {
					Direction.NORTH
					, Direction.EAST
					};

			@Override
			public void visit(Cell cell) {
				List<Cell> list = cell.getNeighbors(dirs);
				logger.info("");
				logger.info("Current cell " + cell + " has neighbors " + list);
				if (list.size() == 0) {
					return;
				}
				int index = random.nextInt(list.size());
				logger.info("Random connecting to neighbor in " + cell.getNeighborDirection(list.get(index)) + ": " + list.get(index));
				list.get(index).link(cell);
			}

			@Override
			public void nextRow() {
				// TODO No implementation needed
			}
			
		});
	}


	@Override
	public Class<?> getCreator() {
		return this.getClass();
	}

}
