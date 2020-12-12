package com.github.verhagen.mazes4p.core;

public interface CellVisitor {

	void nextRow();

	void visit(Cell cell);

}
