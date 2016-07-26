package main.core.parameters.exit;

import main.core.enums.ExitType;

public class ExitParameters {

	private final FixedStopLossParameters fixedStopLoss;
	private final FixedStopGainParameters fixedStopGain;
	private final ExitType exitType;

	public ExitParameters(ExitType exitType, FixedStopLossParameters fixedStopLoss, FixedStopGainParameters fixedStopGain) {
		this.exitType = exitType;
		this.fixedStopLoss = fixedStopLoss;
		this.fixedStopGain = fixedStopGain;
	}

	public FixedStopLossParameters getFixedStopLoss() {
		return fixedStopLoss;
	}

	public FixedStopGainParameters getFixedStopGain() {
		return fixedStopGain;
	}

	public ExitType getExitType() {
		return exitType == null ? ExitType.ANY_INDICATOR : exitType;
	}

}
