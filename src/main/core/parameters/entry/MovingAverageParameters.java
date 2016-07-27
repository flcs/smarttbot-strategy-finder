package main.core.parameters.entry;

import main.core.enums.MovingAverageType;

public class MovingAverageParameters {

	private final MovingAverageType type;
	private final int shortPeriods;
	private final int longPeriods;

	public MovingAverageParameters(int shortPeriods, int longPeriods) {
		this(MovingAverageType.SIMPLE, shortPeriods, longPeriods);
	}

	public MovingAverageParameters(MovingAverageType type, int shortPeriods, int longPeriods) {
		this.type = type;
		this.shortPeriods = shortPeriods;
		this.longPeriods = longPeriods;
	}

	public MovingAverageType getType() {
		return type;
	}

	public int getShortPeriods() {
		return shortPeriods;
	}

	public int getLongPeriods() {
		return longPeriods;
	}

}
