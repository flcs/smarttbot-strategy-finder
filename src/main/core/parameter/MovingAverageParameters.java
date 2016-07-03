package main.core.parameter;

public class MovingAverageParameters {
	private final int shortPeriods;
	private final int longPeriods;

	private MovingAverageParameters(int shortPeriods, int longPeriods) {
		this.shortPeriods = shortPeriods;
		this.longPeriods = longPeriods;
	}

	public static MovingAverageParameters of(int shortPeriods, int longPeriods) {
		return new MovingAverageParameters(shortPeriods, longPeriods);
	}

	public int getShortPeriods() {
		return shortPeriods;
	}

	public int getLongPeriods() {
		return longPeriods;
	}

}
