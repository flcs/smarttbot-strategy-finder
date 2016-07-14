package main.core.rules;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.trading.rules.AbstractRule;
import main.core.enums.StopType;

public class StopLossRule extends AbstractRule {

	private final Decimal maxLoss;
	private final StopType stopType;

	private ClosePriceIndicator closePrice;

	public StopLossRule(ClosePriceIndicator closePrice, Decimal maxLoss, StopType stopType) {
		this.closePrice = closePrice;
		this.maxLoss = maxLoss;
		this.stopType = stopType;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		boolean satisfied = false;

		Order entry = getEntryOrder(tradingRecord);
		if (entry != null) {
			Tick tick = closePrice.getTimeSeries().getTick(index);
			Decimal entryPrice = entry.getPrice();
			Decimal loss = Decimal.ZERO;

			if (entry.getType() == OrderType.BUY) {
				Decimal lowPrice = tick.getMinPrice();
				loss = entryPrice.minus(lowPrice);
			} else {
				Decimal highPrice = tick.getMaxPrice();
				loss = highPrice.minus(entryPrice);
			}

			if (stopType == StopType.PERCENTAGE) {
				loss = loss.multipliedBy(Decimal.HUNDRED).dividedBy(entryPrice);
			}

			satisfied = loss.isGreaterThanOrEqual(maxLoss);
		}

		return satisfied;
	}

	public Decimal getExitPrice(TradingRecord tradingRecord) {
		Order entry = this.getEntryOrder(tradingRecord);
		if (entry == null) {
			return null;
		}

		Decimal loss = maxLoss;
		if (stopType == StopType.PERCENTAGE) {
			loss = loss.multipliedBy(entry.getPrice()).dividedBy(Decimal.HUNDRED);
		}

		switch (entry.getType()) {
		case BUY:
			return entry.getPrice().minus(loss);
		case SELL:
			return entry.getPrice().plus(loss);
		default:
			return null;
		}
	}

	private Order getEntryOrder(TradingRecord tradingRecord) {
		if (tradingRecord != null) {
			Trade currentTrade = tradingRecord.getCurrentTrade();

			if (currentTrade.isOpened()) {
				return currentTrade.getEntry();
			}
		}

		return null;
	}

}
