package main.core.parameter;

import eu.verdelhan.ta4j.Decimal;

public class ExitParameters {
	private final Decimal fixedStopLoss;

	public ExitParameters(Decimal fixedStopLoss) {
		this.fixedStopLoss = fixedStopLoss;
	}

	public Decimal getStopLoss() {
		return fixedStopLoss;
	}

}
