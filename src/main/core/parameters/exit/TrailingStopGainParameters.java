package main.core.parameters.exit;

import eu.verdelhan.ta4j.Decimal;
import main.core.enums.StopType;

public class TrailingStopGainParameters {

	private final StopType type;
	private final Decimal trigger;
	private final Decimal distance;

	public TrailingStopGainParameters(StopType type, Decimal trigger, Decimal distance) {
		this.type = type;
		this.trigger = trigger;
		this.distance = distance;
	}

	public StopType getType() {
		return type;
	}

	public Decimal getTrigger() {
		return trigger;
	}

	public Decimal getDistance() {
		return distance;
	}

}
