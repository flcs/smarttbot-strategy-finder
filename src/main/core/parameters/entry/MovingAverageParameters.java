package main.core.parameters.entry;

public class MovingAverageParameters {
	private final int shortPeriods;
	private final int longPeriods;

	public MovingAverageParameters(int shortPeriods, int longPeriods) {
		this.shortPeriods = shortPeriods;
		this.longPeriods = longPeriods;
	}

	public int getShortPeriods() {
		return shortPeriods;
	}

	public int getLongPeriods() {
		return longPeriods;
	}

}
