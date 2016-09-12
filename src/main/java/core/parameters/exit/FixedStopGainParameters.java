package core.parameters.exit;

import core.definitions.Chromosome;
import core.enums.StopType;
import core.gene.Gene;
import core.genetic.StopDecimalFactory;
import eu.verdelhan.ta4j.Decimal;

public class FixedStopGainParameters implements Chromosome {

	private StopType type;

	@Gene(factory = StopDecimalFactory.class)
	private Decimal value;

	public FixedStopGainParameters() {
	}

	public FixedStopGainParameters(StopType type, Decimal value) {
		this.type = type;
		this.value = value;
	}

	public StopType getType() {
		return type;
	}

	public Decimal getValue() {
		return value;
	}
}
