package com.github.verhagen.mazes4p.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Summary {
	private Grid grid;
	private MazeCreator mazeCreator;
	private List<String> files = new ArrayList<>();


	public Summary(Grid grid, MazeCreator mazeCreator, List<String> files) {
		this.grid = grid;
		this.mazeCreator = mazeCreator;
		this.files = files;
	}


	public Summary() {
	}


	public Grid getGrid() {
		return grid;
	}


	public void setGrid(Grid grid) {
		this.grid = grid;
	}


	public MazeCreator getMazeCreator() {
		return mazeCreator;
	}


	public void setMazeCreator(MazeCreator mazeCreator) {
		this.mazeCreator = mazeCreator;
	}


	public void addFile(Path filePath) {
		String fileName = filePath.toString();
		if (fileName.lastIndexOf("/") > -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"));
		}

		files.add(fileName);
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

}
