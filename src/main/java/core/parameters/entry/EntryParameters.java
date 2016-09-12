package core.parameters.entry;

import core.definitions.Chromosome;
import core.gene.GeneNullable;

public class EntryParameters implements Chromosome {

	@GeneNullable
	private MovingAverageParameters movingAverageParameters;

	@GeneNullable
	private RSIParameters rsiParameters;

	@GeneNullable
	private BollingerBandsParameters bollingerBandsParameters;

	public EntryParameters() {
	}

	public EntryParameters(MovingAverageParameters movingAverageParameters, RSIParameters rsiParameters,
			BollingerBandsParameters bollingerBandsParameters) {
		this.movingAverageParameters = movingAverageParameters;
		this.rsiParameters = rsiParameters;
		this.bollingerBandsParameters = bollingerBandsParameters;
	}

	public MovingAverageParameters getMovingAverageParameters() {
		return movingAverageParameters;
	}

	public RSIParameters getRsiParameters() {
		return rsiParameters;
	}

	public BollingerBandsParameters getBollingerBandsParameters() {
		return bollingerBandsParameters;
	}

}
