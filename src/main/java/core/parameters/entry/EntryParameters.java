package core.parameters.entry;

import core.definitions.Chromosome;

public class EntryParameters implements Chromosome {

	private MovingAverageParameters movingAverageParameters;
	private RSIParameters rsiParameters;
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
