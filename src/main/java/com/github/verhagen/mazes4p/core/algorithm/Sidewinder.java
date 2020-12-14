package com.github.verhagen.mazes4p.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.verhagen.mazes4p.core.Cell;
import com.github.verhagen.mazes4p.core.CellVisitor;
import com.github.verhagen.mazes4p.core.Direction;
import com.github.verhagen.mazes4p.core.Grid;
import com.github.verhagen.mazes4p.core.MazeCreator;

public class Sidewinder implements MazeCreator {
	private static Logger logger = LoggerFactory.getLogger(Sidewinder.class);

	@Override
	public void on(Grid grid) {
		grid.accept(new CellVisitor() {
			private Random random = new Random();
			private List<Cell> run = new ArrayList<>();

			@Override
			public void visit(Cell cell) {
				run.add(cell);
				boolean atEastBoundry = ! cell.isNeighborAvailable(Direction.EAST);
				boolean atNorthBoundry = ! cell.isNeighborAvailable(Direction.NORTH);
				
				boolean shouldCloseOut = atEastBoundry || !atNorthBoundry && random.nextInt(2) == 0;
				logger.info("Cell" + cell 
						+ "  should" + (shouldCloseOut ? "    " : " not" ) + " close out"
						+ (atNorthBoundry ? "  atNorthBoundry" : "")
						+ (atEastBoundry ? "  atEastBoundry" : "")
						);
				if (atEastBoundry && atNorthBoundry) {
					return;
				}

				if (shouldCloseOut) {
					if (run.size() > 0) {
						int index = (run.size() > 1) ? random.nextInt(run.size()) : 0;
						Cell member = run.get(index);
						member.link(member.getNeighbors(Direction.NORTH));
					}
					run.clear();
				}
				else {
					cell.link(cell.getNeighbors(Direction.EAST));
				}
			}

			@Override
			public void nextRow() {
				run.clear();
			}
			
		});
		
	}

}
