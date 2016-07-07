package main.core.parameters;

import main.core.parameters.exit.FixedStopLossParameters;

public class ExitParameters {
	private final FixedStopLossParameters fixedStopLoss;

	public ExitParameters(FixedStopLossParameters fixedStopLoss) {
		this.fixedStopLoss = fixedStopLoss;
	}

	public FixedStopLossParameters getStopLoss() {
		return fixedStopLoss;
	}

}
