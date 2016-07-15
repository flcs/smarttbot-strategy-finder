package main.core.parameters.exit;

import eu.verdelhan.ta4j.Decimal;
import main.core.enums.StopType;

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
