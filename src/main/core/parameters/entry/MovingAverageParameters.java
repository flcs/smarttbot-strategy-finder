package main.core.parameters.entry;

import main.core.enums.MovingAverageType;

public class MovingAverageParameters {

	private final MovingAverageType shortType;
	private final MovingAverageType longType;
	private final int shortPeriods;
	private final int longPeriods;

	public MovingAverageParameters(int shortPeriods, int longPeriods) {
		this(MovingAverageType.SIMPLE, shortPeriods, longPeriods);
	}

	public MovingAverageParameters(MovingAverageType shortType, MovingAverageType longType, int shortPeriods,
			int longPeriods) {
		this.shortType = shortType;
		this.longType = longType;
		this.shortPeriods = shortPeriods;
		this.longPeriods = longPeriods;
	}

	public MovingAverageParameters(MovingAverageType type, int shortPeriods, int longPeriods) {
		this(type, type, shortPeriods, longPeriods);
	}

	public MovingAverageType getShortType() {
		return shortType;
	}

	public MovingAverageType getLongType() {
		return longType;
	}

	public int getShortPeriods() {
		return shortPeriods;
	}

	public int getLongPeriods() {
		return longPeriods;
	}

}
