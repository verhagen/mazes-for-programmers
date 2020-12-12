package com.github.verhagen.mazes4p.core;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryTree {
	private static Logger logger = LoggerFactory.getLogger(BinaryTree.class);
	private Random random = new Random();

	public void on(Grid grid) {
		grid.accept(new CellVisitor() {
			Direction[] dirs = new Direction[] {
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
}
