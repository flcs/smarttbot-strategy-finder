package core.parameters.entry;

import core.definitions.Chromosome;
import eu.verdelhan.ta4j.Decimal;

public class BollingerBandsParameters implements Chromosome {

	private int periods;
	private Decimal factor;

	public BollingerBandsParameters() {
	}

	public BollingerBandsParameters(int periods, Decimal factor) {
		this.periods = periods;
		this.factor = factor;
	}

	public int getPeriods() {
		return periods;
	}

	public Decimal getFactor() {
		return factor;
	}

}
