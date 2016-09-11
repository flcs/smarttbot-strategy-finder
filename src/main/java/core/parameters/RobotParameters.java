package core.parameters;

import core.definitions.Individual;
import core.enums.ExitType;
import core.parameters.daytrade.DayTradeParameters;
import core.parameters.entry.EntryParameters;
import core.parameters.exit.ExitParameters;

public class RobotParameters implements Individual<RobotParameters> {

	private EntryParameters entryParameters;
	private ExitParameters exitParameters;
	private DayTradeParameters dayTradeParameters;

	public RobotParameters() {
	}

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

	@Override
	public int compareTo(RobotParameters o) {
		return 0;
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
