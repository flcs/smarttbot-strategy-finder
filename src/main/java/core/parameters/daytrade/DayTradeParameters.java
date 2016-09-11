package core.parameters.daytrade;

import java.time.LocalTime;

public class DayTradeParameters {
	private final LocalTime initialEntryTimeLimit;
	private final LocalTime finalEntryTimeLimit;
	private final LocalTime exitTimeLimit;

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
