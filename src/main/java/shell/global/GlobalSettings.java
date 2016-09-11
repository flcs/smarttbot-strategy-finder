package shell.global;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;

public class GlobalSettings {

	protected static TimeSeries timeSeries;
	protected static Decimal numberOfContracts = Decimal.valueOf(1);

	public static TimeSeries timeSeries() {
		return timeSeries;
	}

	public static Decimal numberOfContracts() {
		return numberOfContracts;
	}

}
