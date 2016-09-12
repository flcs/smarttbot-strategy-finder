package core.parameters.daytrade;

import java.time.LocalTime;

import core.definitions.Chromosome;
import core.gene.Gene;
import core.gene.GeneNullable;
import core.genetic.LocalTimeFactory;

public class DayTradeParameters implements Chromosome {

	@GeneNullable
	@Gene(factory = LocalTimeFactory.class)
	private LocalTime initialEntryTimeLimit;

	@GeneNullable
	@Gene(factory = LocalTimeFactory.class)
	private LocalTime finalEntryTimeLimit;

	@Gene(factory = LocalTimeFactory.class)
	private LocalTime exitTimeLimit;

	public DayTradeParameters() {
	}

	public DayTradeParameters(LocalTime initialEntryTimeLimit, LocalTime finalEntryTimeLimit, LocalTime exitTimeLimit) {
		this.initialEntryTimeLimit = initialEntryTimeLimit;
		this.finalEntryTimeLimit = finalEntryTimeLimit;
		this.exitTimeLimit = exitTimeLimit;
	}

	public LocalTime getInitialEntryTimeLimit() {
		return initialEntryTimeLimit;
	}

	public LocalTime getFinalEntryTimeLimit() {
		return finalEntryTimeLimit;
	}

	public LocalTime getExitTimeLimit() {
		return exitTimeLimit;
	}

}
