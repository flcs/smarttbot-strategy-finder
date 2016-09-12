package core.parameters.entry;

import core.attributes.ArgInteger;
import core.definitions.Chromosome;
import core.gene.Gene;
import core.genetic.DecimalFactory;
import eu.verdelhan.ta4j.Decimal;

public class BollingerBandsParameters implements Chromosome {

	private int periods;

	@Gene(factory = DecimalFactory.class, argsInteger = { @ArgInteger(name = "min", value = 0),
			@ArgInteger(name = "max", value = 500), @ArgInteger(name = "precision", value = 100) })
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
