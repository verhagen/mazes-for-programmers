package com.github.verhagen.mazes4p.core.creator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.MazeCreator;

public class AldousBroder implements MazeCreator {
	private Logger logger = LoggerFactory.getLogger(AldousBroder.class);


	@Override
	public void on(Grid grid) {
		int unvisitedCells = grid.size() - 1;
		Cell cell = grid.getRandomCell();
		while (unvisitedCells > 0) {
			logger.info("Cell " + cell + "    " + unvisitedCells);
			List<Cell> neighbors = new ArrayList<>(cell.getNeighbors());
			Cell neighbor = neighbors.get(grid.getRandom().nextInt(neighbors.size()));
			if (neighbor.getLinks().isEmpty()) {
				cell.link(neighbor);
				unvisitedCells -= 1;
			}
			cell = neighbor;
		}
	}

	@Override
	public Class<?> getCreator() {
		return AldousBroder.class;
	}

}
