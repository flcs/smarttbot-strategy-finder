package core.parameters.exit;

import core.enums.StopType;
import eu.verdelhan.ta4j.Decimal;

public class FixedStopGainParameters {

	private final StopType type;
	private final Decimal value;

	public FixedStopGainParameters(StopType type, Decimal value) {
		this.type = type;
		this.value = value;
	}

	public StopType getType() {
		return type;
	}

	public Decimal getValue() {
		return value;
	}
}
