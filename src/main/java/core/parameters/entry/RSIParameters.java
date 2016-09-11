package core.parameters.entry;

import core.definitions.Chromosome;

public class RSIParameters implements Chromosome {

	private int periods;
	private int lowerValue;
	private int upperValue;

	public RSIParameters() {
	}

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
