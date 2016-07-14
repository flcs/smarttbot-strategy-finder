package main.core.parameters.exit;

import eu.verdelhan.ta4j.Decimal;
import main.core.enums.StopType;

public class FixedStopLossParameters {

	private final StopType type;
	private final Decimal value;

	public FixedStopLossParameters(StopType type, Decimal value) {
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
