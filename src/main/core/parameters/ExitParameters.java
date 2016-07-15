package main.core.parameters;

import main.core.parameters.exit.FixedStopGainParameters;
import main.core.parameters.exit.FixedStopLossParameters;

public class ExitParameters {

	private final FixedStopLossParameters fixedStopLoss;
	private final FixedStopGainParameters fixedStopGain;

	public ExitParameters(FixedStopLossParameters fixedStopLoss, FixedStopGainParameters fixedStopGain) {
		this.fixedStopLoss = fixedStopLoss;
		this.fixedStopGain = fixedStopGain;
	}

	public FixedStopLossParameters getFixedStopLoss() {
		return fixedStopLoss;
	}

	public FixedStopGainParameters getFixedStopGain() {
		return fixedStopGain;
	}

}
