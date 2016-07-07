package main.core.parameters;

import java.time.LocalTime;

import eu.verdelhan.ta4j.Tick;

public class DayTradeParameters {
	private final LocalTime initialEntryTimeLimit;
	private final LocalTime finalEntryTimeLimit;
	private final LocalTime exitTimeLimit;

	public DayTradeParameters(LocalTime initialEntryTimeLimit, LocalTime finalEntryTimeLimit, LocalTime exitTimeLimit) {
		this.initialEntryTimeLimit = initialEntryTimeLimit;
		this.finalEntryTimeLimit = finalEntryTimeLimit;
		this.exitTimeLimit = exitTimeLimit;
	}

	public boolean canOpenPosition(Tick tick) {
		boolean validInitialTime = true;
		boolean validFinalTime = true;

		if (initialEntryTimeLimit != null)
			validInitialTime = tick.getEndTime().getSecondOfDay() >= initialEntryTimeLimit.toSecondOfDay();

		if (finalEntryTimeLimit != null)
			validFinalTime = tick.getEndTime().getSecondOfDay() < finalEntryTimeLimit.toSecondOfDay();

		return validInitialTime && validFinalTime;
	}

	public boolean forceClosePosition(Tick tick) {
		boolean forceClosePosition = false;

		if (exitTimeLimit != null)
			forceClosePosition = tick.getEndTime().getSecondOfDay() >= exitTimeLimit.toSecondOfDay();

		return forceClosePosition;
	}

}
