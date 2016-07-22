package main.core.parameters.exit;

public class ExitParameters {

	private final FixedStopLossParameters fixedStopLoss;
	private final FixedStopGainParameters fixedStopGain;
	private final TrailingStopGainParameters trailingStopGain;

	public ExitParameters(FixedStopLossParameters fixedStopLoss, FixedStopGainParameters fixedStopGain,
			TrailingStopGainParameters trailingStopGain) {
		this.fixedStopLoss = fixedStopLoss;
		this.fixedStopGain = fixedStopGain;
		this.trailingStopGain = trailingStopGain;
	}

	public ExitParameters(FixedStopLossParameters fixedStopLoss, FixedStopGainParameters fixedStopGain) {
		this.fixedStopLoss = fixedStopLoss;
		this.fixedStopGain = fixedStopGain;
		this.trailingStopGain = null;
	}

	public FixedStopLossParameters getFixedStopLoss() {
		return fixedStopLoss;
	}

	public FixedStopGainParameters getFixedStopGain() {
		return fixedStopGain;
	}

	public TrailingStopGainParameters getTrailingStopGain() {
		return trailingStopGain;
	}

}
