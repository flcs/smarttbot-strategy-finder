package core.parameters;

import core.enums.ExitType;
import core.parameters.daytrade.DayTradeParameters;
import core.parameters.entry.EntryParameters;
import core.parameters.exit.ExitParameters;

public class RobotParameters {
	private final EntryParameters entryParameters;
	private final ExitParameters exitParameters;
	private final DayTradeParameters dayTradeParameters;

	public RobotParameters(EntryParameters entryParameters) {
		this(entryParameters, new ExitParameters(ExitType.ANY_INDICATOR, null, null), null);
	}

	public RobotParameters(EntryParameters entryParameters, DayTradeParameters dayTradeParameters) {
		this(entryParameters, new ExitParameters(ExitType.ANY_INDICATOR, null, null), dayTradeParameters);
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

}
