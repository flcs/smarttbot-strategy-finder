package main.core.rules;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
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

		if (tradingRecord != null) {
			Trade currentTrade = tradingRecord.getCurrentTrade();

			if (currentTrade.isOpened()) {
				Decimal entryPrice = currentTrade.getEntry().getPrice();
				Decimal currentPrice = closePrice.getValue(index);

				Decimal loss = Decimal.ZERO;
				if (currentTrade.getEntry().getType() == OrderType.BUY) {
					loss = entryPrice.minus(currentPrice);
				} else {
					loss = currentPrice.minus(entryPrice);
				}

				satisfied = loss.isGreaterThanOrEqual(maxLoss);
			}
		}

		return satisfied;
	}
}
