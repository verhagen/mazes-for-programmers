package com.github.verhagen.mazes4p.core.creator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.CellVisitor;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.MazeCreator;

public class Wilson implements MazeCreator {
	private Logger logger = LoggerFactory.getLogger(Wilson.class);
	private Random random;


	@Override
	public void on(Grid grid) {
		random = grid.getRandom();
		List<Cell> unvisited = new ArrayList<>();
		
		grid.accept(new CellVisitor() {
			
			@Override
			public void visit(Cell cell) {
				unvisited.add(cell);
			}
			
			@Override
			public void nextRow() {
				// No implementation needed
			}
		});

		Cell first = random(unvisited);
		unvisited.remove(first);

		while (! unvisited.isEmpty()) {
			Cell cell = random(unvisited);  
			List<Cell> prospectPath = new ArrayList<>();
			prospectPath.add(cell);
			while (unvisited.contains(cell)) {
				cell = random(cell.getNeighbors());
				if (prospectPath.contains(cell)) {
					logger.info("prospectPath current: " + prospectPath + "  loop " + cell);
					int index = prospectPath.indexOf(cell);
					prospectPath.subList(index + 1, prospectPath.size()).clear();
					logger.info("prospectPath cleanup: " + prospectPath);
				}
				else {
					prospectPath.add(cell);
				}
			}

			logger.info("Adding path: " + prospectPath);
			for (int index = 0; index < prospectPath.size() - 1; index++) {
				prospectPath.get(index).link(prospectPath.get(index + 1));
				unvisited.remove(prospectPath.get(index));
			}
			logger.info("unvisited: " + unvisited);
		}
	}

	private Cell random(Collection<Cell> coll) {
		return random(new ArrayList<>(coll));
	}

	private Cell random(List<Cell> cells) {
		return cells.get(random.nextInt(cells.size()));
	}

	@Override
	public Class<?> getCreator() {
		return Wilson.class;
	}

}
