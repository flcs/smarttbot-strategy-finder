package core.parameters.exit;

import core.definitions.Chromosome;
import core.enums.StopType;
import eu.verdelhan.ta4j.Decimal;

public class TrailingStopGainParameters implements Chromosome {

	private StopType type;
	private Decimal trigger;
	private Decimal distance;

	public TrailingStopGainParameters() {
	}

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
