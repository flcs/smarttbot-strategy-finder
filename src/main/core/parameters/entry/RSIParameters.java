package main.core.parameters.entry;

public class RSIParameters {
	private final int periods;
	private final int lowerValue;
	private final int upperValue;

	public RSIParameters(int periods, int lowerValue, int upperValue) {
		this.periods = periods;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
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
