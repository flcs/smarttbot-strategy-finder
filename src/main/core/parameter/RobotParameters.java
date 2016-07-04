package main.core.parameter;

import eu.verdelhan.ta4j.Tick;

public class RobotParameters {
	private final EntryParameters entryParameters;
	private final DayTradeParameters dayTradeParameters;

	public RobotParameters(EntryParameters entryParameters) {
		this(entryParameters, null);
	}

	public RobotParameters(EntryParameters entryParameters, DayTradeParameters dayTradeParameters) {
		this.entryParameters = entryParameters;
		this.dayTradeParameters = dayTradeParameters;
	}

	public EntryParameters getEntryParameters() {
		return entryParameters;
	}

	public DayTradeParameters getDayTradeParameters() {
		return dayTradeParameters;
	}

	public boolean exitTimeLimit(Tick tick) {
		return dayTradeParameters != null && dayTradeParameters.exitTimeLimit(tick);
	}
}
