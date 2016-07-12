package main.core.rules;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.trading.rules.AbstractRule;

public class AbsoluteStopLossRule extends AbstractRule {

	private ClosePriceIndicator closePrice;
	private Decimal maxLoss;

	public AbsoluteStopLossRule(ClosePriceIndicator closePrice, Decimal maxLoss) {
		this.closePrice = closePrice;
		this.maxLoss = maxLoss;
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

			satisfied = loss.isGreaterThanOrEqual(maxLoss);
		}

		return satisfied;
	}

	public Decimal getExitPrice(TradingRecord tradingRecord) {
		Order entry = this.getEntryOrder(tradingRecord);

		if (entry != null && entry.getType() == OrderType.BUY) {
			return entry.getPrice().minus(maxLoss);
		} else if (entry != null && entry.getType() == OrderType.SELL) {
			return entry.getPrice().plus(maxLoss);
		}

		return null;
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
