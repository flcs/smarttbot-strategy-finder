package main.core.parameter;

public class RSIParameters {
	private final int periods;
	private final int lowerValue;
	private final int upperValue;

	private RSIParameters(int periods, int lowerValue, int upperValue) {
		this.periods = periods;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}

	public static RSIParameters of(int periods, int lowerValue, int upperValue) {
		return new RSIParameters(periods, lowerValue, upperValue);
	}

	public int getPeriods() {
		return periods;
	}

	public int getLowerValue() {
		return lowerValue;
	}

	public int getUpperValue() {
		return upperValue;
	}

}
