package com.github.verhagen.mazes4p.core.creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.MazeCreator;

public class HundAndKill implements MazeCreator {
	private static Logger logger = LoggerFactory.getLogger(HundAndKill.class);
	private Random random;


	@Override
	public void on(Grid grid) {
		random = grid.getRandom();
		Cell current = grid.getRandomCell();
		while (current != null) {
			List<Cell> unvisitedNeighbors = getUnvisitedNeighbors(current);
			logger.info(current + "  Unvisited Neighbors: " + unvisitedNeighbors);
			if (! unvisitedNeighbors.isEmpty()) {
				Cell neighbor = random(unvisitedNeighbors);
				logger.info("  connect " + neighbor);
				current.link(neighbor);
				current = neighbor;
			}
			else {
				current = null;

				for (int r = 0; r < grid.getRows(); r++) {
					for (int c = 0; c < grid.getColumns(); c++) {
						Cell cell = grid.get(r, c);
						List<Cell> visitedNeighbors = getVisitedNeighbors(cell);
						if (cell.getLinks().isEmpty() && visitedNeighbors.size() > 0) {
							current = cell;
							cell.link(random(visitedNeighbors));
							break;
						}
					}
				}
			}
		}
	}

	private List<Cell> getUnvisitedNeighbors(Cell cell) {
		List<Cell> unvisitedNeighbors = new ArrayList<>(); 
		for (Cell neighbor : cell.getNeighbors()) {
			if (neighbor.getLinks().isEmpty()) {
				unvisitedNeighbors.add(neighbor);
			}
		}
		return unvisitedNeighbors;
	}

	private List<Cell> getVisitedNeighbors(Cell cell) {
		List<Cell> visitedNeighbors = new ArrayList<>(); 
		for (Cell neighbor : cell.getNeighbors()) {
			if (neighbor.getLinks().size() > 0) {
				visitedNeighbors.add(neighbor);
			}
		}
		return visitedNeighbors;
	}

	@Override
	public Class<?> getCreator() {
		// TODO Auto-generated method stub
		return null;
	}

	private Cell random(List<Cell> cells) {
		return cells.get(random.nextInt(cells.size()));
	}

}
