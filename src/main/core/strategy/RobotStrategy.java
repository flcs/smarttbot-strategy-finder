package main.core.strategy;

import java.util.ArrayList;
import java.util.List;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.parameters.RobotParameters;

public class RobotStrategy {
	private static final Decimal NumberOfContracts = Decimal.valueOf(1);

	public static List<Trade> backtest(TimeSeries series, RobotParameters parameters) {
		ClosePriceIndicator closePrices = new ClosePriceIndicator(series);
		StrategyRules rules = new StrategyRules(closePrices, parameters);

		return run(series, rules, parameters);
	}

	private static List<Trade> run(TimeSeries series, StrategyRules rules, RobotParameters param) {
		List<Trade> trades = new ArrayList<>();

		TradingRecord buyingRecord = new TradingRecord(OrderType.BUY);
		TradingRecord sellingRecord = new TradingRecord(OrderType.SELL);

		boolean bought = false;
		boolean sold = false;

		for (int i = series.getBegin(); i < series.getEnd(); i++) {
			Decimal buyOperate = rules.buyOperate(i, buyingRecord);
			Decimal sellOperate = rules.sellOperate(i, sellingRecord);

			if (buyOperate != null && !bought && sellOperate != null && !sold)
				buyOperate = sellOperate = null;

			if (buyOperate != null) {
				buyingRecord.operate(i, buyOperate, NumberOfContracts);
				bought = !bought;

				if (bought) {
					rules.startNewTrade();
				} else {
					trades.add(buyingRecord.getLastTrade());
				}
			}
			if (sellOperate != null) {
				sellingRecord.operate(i, sellOperate, NumberOfContracts);
				sold = !sold;

				if (sold) {
					rules.startNewTrade();
				} else {
					trades.add(sellingRecord.getLastTrade());
				}
			}
		}

		if (bought) {
			buyingRecord.operate(series.getEnd(), series.getTick(series.getEnd()).getClosePrice(), NumberOfContracts);
			trades.add(buyingRecord.getLastTrade());
		}

		if (sold) {
			sellingRecord.operate(series.getEnd(), series.getTick(series.getEnd()).getClosePrice(), NumberOfContracts);
			trades.add(sellingRecord.getLastTrade());
		}

		return trades;
	}
}
