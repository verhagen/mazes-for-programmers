package com.github.verhagen.mazes4p.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sidewinder {
	private static Logger logger = LoggerFactory.getLogger(Sidewinder.class);
	private Random random = new Random();

	public void on(Grid grid) {
		grid.accept(new CellVisitor() {
			List<Cell> run = new ArrayList<>();

			@Override
			public void visit(Cell cell) {
				run.add(cell);
				boolean atEastBoundry = ! cell.isNeighborAvailable(Direction.EAST);
				boolean atNorthBoundry = ! cell.isNeighborAvailable(Direction.NORTH);
				
				boolean shouldCloseOut = atEastBoundry || !atNorthBoundry && random.nextInt(2) == 0; //(! atNorthBoundry  && random.nextInt(2) == 0);
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
