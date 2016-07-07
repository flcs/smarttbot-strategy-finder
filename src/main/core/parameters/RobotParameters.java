package main.core.parameters;

import eu.verdelhan.ta4j.Tick;

public class RobotParameters {
	private final EntryParameters entryParameters;
	private final ExitParameters exitParameters;
	private final DayTradeParameters dayTradeParameters;

	public RobotParameters(EntryParameters entryParameters) {
		this(entryParameters, null, null);
	}

	public RobotParameters(EntryParameters entryParameters, DayTradeParameters dayTradeParameters) {
		this(entryParameters, null, dayTradeParameters);
	}

	public RobotParameters(EntryParameters entryParameters, ExitParameters exitParameters) {
		this(entryParameters, exitParameters, null);
	}

	public RobotParameters(EntryParameters entryParameters, ExitParameters exitParameters,
			DayTradeParameters dayTradeParameters) {
		this.entryParameters = entryParameters;
		this.exitParameters = exitParameters;
		this.dayTradeParameters = dayTradeParameters;
	}

	public EntryParameters getEntryParameters() {
		return entryParameters;
	}

	public ExitParameters getExitParameters() {
		return exitParameters;
	}

	public DayTradeParameters getDayTradeParameters() {
		return dayTradeParameters;
	}

	public boolean canOpenPosition(Tick tick) {
		return dayTradeParameters == null || dayTradeParameters.canOpenPosition(tick);
	}

	public boolean forceClosePosition(Tick tick) {
		return dayTradeParameters != null && dayTradeParameters.forceClosePosition(tick);
	}

}
