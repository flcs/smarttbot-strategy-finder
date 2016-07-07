package main.core.parameters;

import main.core.parameters.entry.BollingerBandsParameters;
import main.core.parameters.entry.MovingAverageParameters;
import main.core.parameters.entry.RSIParameters;

public class EntryParameters {
	private final MovingAverageParameters movingAverageParameters;
	private final RSIParameters rsiParameters;
	private final BollingerBandsParameters bollingerBandsParameters;

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
