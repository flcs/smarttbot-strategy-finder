package core.parameters.exit;

import core.definitions.Chromosome;
import core.enums.ExitType;
import core.gene.GeneNullable;

public class ExitParameters implements Chromosome {

	private ExitType exitType;
	
	@GeneNullable
	private FixedStopLossParameters fixedStopLoss;

	@GeneNullable
	private FixedStopGainParameters fixedStopGain;

	@GeneNullable
	private TrailingStopGainParameters trailingStopGain;

	public ExitParameters() {
	}

	public ExitParameters(ExitType exitType, FixedStopLossParameters fixedStopLoss,
			FixedStopGainParameters fixedStopGain, TrailingStopGainParameters trailingStopGain) {
		this.exitType = exitType;
		this.fixedStopLoss = fixedStopLoss;
		this.fixedStopGain = fixedStopGain;
		this.trailingStopGain = trailingStopGain;
	}

	public ExitParameters(ExitType exitType, FixedStopLossParameters fixedStopLoss,
			FixedStopGainParameters fixedStopGain) {
		this.exitType = exitType;
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

	public ExitType getExitType() {
		return exitType == null ? ExitType.ANY_INDICATOR : exitType;
	}

	public TrailingStopGainParameters getTrailingStopGain() {
		return trailingStopGain;
	}

}
