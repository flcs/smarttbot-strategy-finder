package main.core.parameters.entry;

import eu.verdelhan.ta4j.Decimal;

public class BollingerBandsParameters {
	private final int periods;
	private final Decimal factor;

	public BollingerBandsParameters(int periods, Decimal factor) {
		this.periods = periods;
		this.factor = factor;
	}

	public int getPeriods() {
		return periods;
	}

	public Decimal getFactor() {
		return factor;
	}

}
