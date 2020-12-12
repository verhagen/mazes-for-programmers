package com.github.verhagen.mazes4p.core;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
	NORTH
	, SOUTH
	, WEST
	, EAST
	;

	private static final Map<Integer, Direction> DIRECTION_AS_NUMBER = new HashMap<>();
	
	static {
		DIRECTION_AS_NUMBER.put(0, NORTH);
		DIRECTION_AS_NUMBER.put(1, SOUTH);
		DIRECTION_AS_NUMBER.put(2, WEST);
		DIRECTION_AS_NUMBER.put(4, EAST);
	}
	
	public static Direction to(int index) {
		if (index < 0 || index > 3) {
			throw new IllegalArgumentException("Argument 'index' should be one of [0..3].");
		}
		return DIRECTION_AS_NUMBER.get(index);
	}
}
