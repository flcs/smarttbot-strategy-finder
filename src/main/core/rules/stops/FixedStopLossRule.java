package main.core.rules.stops;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.enums.StopType;

public class FixedStopLossRule extends AbstractStopRule {

	public FixedStopLossRule(ClosePriceIndicator closePrice, Decimal maxLoss, StopType stopType) {
		super(closePrice, maxLoss, stopType);
	}

	@Override
	protected Decimal getResult(Order entry, Tick tick) {
		Decimal entryPrice = entry.getPrice();

		switch (entry.getType()) {
		case BUY:
			Decimal lowPrice = tick.getMinPrice();
			return entryPrice.minus(lowPrice);
		case SELL:
			Decimal highPrice = tick.getMaxPrice();
			return highPrice.minus(entryPrice);
		default:
			throw new IllegalArgumentException("Entry order must be either a buying order or selling order.");
		}
	}

	@Override
	protected Decimal getExitPrice(Order entry, Decimal loss) {
		Decimal entryPrice = entry.getPrice();

		switch (entry.getType()) {
		case BUY:
			return entryPrice.minus(loss);
		case SELL:
			return entryPrice.plus(loss);
		default:
			throw new IllegalArgumentException("Entry order must be either a buying order or selling order.");
		}
	}

}
