package main.core.parameters.exit;

import main.core.enums.ExitType;

public class ExitParameters {

	private final FixedStopLossParameters fixedStopLoss;
	private final FixedStopGainParameters fixedStopGain;

	public ExitParameters(ExitType exitType, FixedStopLossParameters fixedStopLoss, FixedStopGainParameters fixedStopGain) {
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
