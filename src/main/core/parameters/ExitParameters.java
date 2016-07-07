package main.core.parameters;

public class ExitParameters {
	private final FixedStopLossParameters fixedStopLoss;

	public ExitParameters(FixedStopLossParameters fixedStopLoss) {
		this.fixedStopLoss = fixedStopLoss;
	}

	public FixedStopLossParameters getStopLoss() {
		return fixedStopLoss;
	}

}
