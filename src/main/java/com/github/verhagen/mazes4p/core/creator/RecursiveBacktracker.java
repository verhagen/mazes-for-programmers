package com.github.verhagen.mazes4p.core.creator;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.MazeCreator;

public class RecursiveBacktracker implements MazeCreator {
	private static Logger logger = LoggerFactory.getLogger(RecursiveBacktracker.class);
	private Random random;

	@Override
	public void on(Grid grid) {
		random = grid.getRandom();
		Stack<Cell>  stack = new Stack<>();
		stack.push(grid.getRandomCell());
		while (! stack.isEmpty()) {
			Cell current = stack.peek();
			List<Cell> unvisitedNeighbors = getUnvisitedNeighbors(current);
			logger.info("Unvisited Neighbors: " + unvisitedNeighbors);
			if (unvisitedNeighbors.isEmpty()) {
				stack.pop();
			}
			else {
				Cell neighbor = random(unvisitedNeighbors);
				current.link(neighbor);
				stack.push(neighbor);
			}
		}
	}

	private List<Cell> getUnvisitedNeighbors(Cell current) {
		return current.getNeighbors().stream().filter(n -> n.getLinks().isEmpty()).collect(Collectors.toList());
	}

	@Override
	public Class<?> getCreator() {
		return RecursiveBacktracker.class;
	}

	private Cell random(List<Cell> cells) {
		return cells.get(random.nextInt(cells.size()));
	}

}
