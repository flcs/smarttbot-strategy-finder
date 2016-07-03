package main.core.strategy;

import java.util.ArrayList;
import java.util.List;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.trading.rules.CrossedDownIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.CrossedUpIndicatorRule;
import main.core.parameter.RobotParameters;

public class RobotStrategy {
	private static final Decimal NumberOfContracts = Decimal.valueOf(1);

	public static List<Trade> backtest(TimeSeries series, RobotParameters parameters) {
		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

		int shortPeriods = parameters.getMovingAverageParameters().getShortPeriods();
		SMAIndicator shortSMA = new SMAIndicator(closePrice, shortPeriods);

		int longPeriods = parameters.getMovingAverageParameters().getLongPeriods();
		SMAIndicator longSMA = new SMAIndicator(closePrice, longPeriods);

		Rule crossDownRule = new CrossedDownIndicatorRule(shortSMA, longSMA);
		Rule crossUpRule = new CrossedUpIndicatorRule(shortSMA, longSMA);

		Strategy buyStrategy = new Strategy(crossUpRule, crossDownRule);
		Strategy sellStrategy = new Strategy(crossDownRule, crossUpRule);

		return run(series, buyStrategy, sellStrategy);
	}

	private static List<Trade> run(TimeSeries series, Strategy buyStrategy, Strategy sellStrategy) {
		List<Trade> trades = new ArrayList<>();

		TradingRecord buyingRecord = new TradingRecord(OrderType.BUY);
		TradingRecord sellingRecord = new TradingRecord(OrderType.SELL);

		boolean bought = false;
		boolean sold = false;

		for (int i = series.getBegin(); i < series.getEnd(); i++) {
			boolean buyOperate = buyStrategy.shouldOperate(i, buyingRecord);
			boolean sellOperate = sellStrategy.shouldOperate(i, sellingRecord);

			if (buyOperate && !bought && sellOperate && !sold)
				buyOperate = sellOperate = false;

			if (buyOperate) {
				buyingRecord.operate(i, series.getTick(i).getClosePrice(), NumberOfContracts);
				if (bought)
					trades.add(buyingRecord.getLastTrade());
				bought = !bought;
			}
			if (sellOperate) {
				sellingRecord.operate(i, series.getTick(i).getClosePrice(), NumberOfContracts);
				if (bought)
					trades.add(sellingRecord.getLastTrade());
				sold = !sold;
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
