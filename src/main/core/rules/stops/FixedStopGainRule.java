package main.core.rules.stops;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.enums.StopType;

public class FixedStopGainRule extends AbstractStopRule {

	public FixedStopGainRule(ClosePriceIndicator closePrice, Decimal maxGain, StopType stopType) {
		super(closePrice, maxGain, stopType);
	}

	@Override
	protected Decimal getResult(Order entry, Tick tick) {
		Decimal entryPrice = entry.getPrice();

		switch (entry.getType()) {
		case BUY:
			Decimal highPrice = tick.getMaxPrice();
			return highPrice.minus(entryPrice);
		case SELL:
			Decimal lowPrice = tick.getMinPrice();
			return entryPrice.minus(lowPrice);
		default:
			throw new IllegalArgumentException("Entry order must be either a buying order or selling order.");
		}
	}

	@Override
	protected Decimal getExitPrice(Order entry) {
		Decimal entryPrice = entry.getPrice();
		Decimal profit = this.resultLimit;
		if (this.stopType == StopType.PERCENTAGE) {
			profit = profit.multipliedBy(entryPrice).dividedBy(Decimal.HUNDRED);
		}

		switch (entry.getType()) {
		case BUY:
			return entryPrice.plus(profit);
		case SELL:
			return entryPrice.minus(profit);
		default:
			throw new IllegalArgumentException("Entry order must be either a buying order or selling order.");
		}
	}

}
