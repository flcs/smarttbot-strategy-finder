package main.core.parameter;

import java.time.LocalTime;

import eu.verdelhan.ta4j.Tick;

public class DayTradeParameters {
	private final LocalTime exitTimeLimit;

	public DayTradeParameters(LocalTime exitTimeLimit) {
		this.exitTimeLimit = exitTimeLimit;
	}

	public LocalTime getExitTimeLimit() {
		return exitTimeLimit;
	}

	public boolean exitTimeLimit(Tick tick) {
		return exitTimeLimit != null && tick.getEndTime().getSecondOfDay() >= exitTimeLimit.toSecondOfDay();
	}

}
