package com.github.verhagen.mazes4p.core;

public interface GridVisitor extends CellVisitor {

	void begin(Grid grid);
	void end(Grid grid);

}
