package main.core.parameter;

import java.time.LocalTime;

import eu.verdelhan.ta4j.Tick;

public class DayTradeParameters {
	private final LocalTime initialEntryTimeLimit;
	private final LocalTime exitTimeLimit;

	public DayTradeParameters(LocalTime initialEntryTimeLimit, LocalTime exitTimeLimit) {
		this.initialEntryTimeLimit = initialEntryTimeLimit;
		this.exitTimeLimit = exitTimeLimit;
	}

	public boolean canOperate(Tick tick) {
		boolean canOperate = true;

		if (initialEntryTimeLimit != null)
			canOperate = canOperate && tick.getEndTime().getSecondOfDay() >= initialEntryTimeLimit.toSecondOfDay();

		if (exitTimeLimit != null)
			canOperate = canOperate && tick.getEndTime().getSecondOfDay() < exitTimeLimit.toSecondOfDay();

		return canOperate;
	}

}
