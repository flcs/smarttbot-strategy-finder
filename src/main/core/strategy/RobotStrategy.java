package main.core.strategy;

import java.util.ArrayList;
import java.util.List;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.parameter.RobotParameters;

public class RobotStrategy {
	private static final Decimal NumberOfContracts = Decimal.valueOf(1);

	public static List<Trade> backtest(TimeSeries series, RobotParameters parameters) {
		ClosePriceIndicator closePrices = new ClosePriceIndicator(series);
		StrategyRules rules = new StrategyRules(closePrices, parameters);

		Strategy buyStrategy = new Strategy(rules.getBuyEntryRule(), rules.getBuyExitRule());
		Strategy sellStrategy = new Strategy(rules.getSellEntryRule(), rules.getSellExitRule());

		return run(series, buyStrategy, sellStrategy, parameters);
	}

	private static List<Trade> run(TimeSeries series, Strategy buyStrategy, Strategy sellStrategy,
			RobotParameters param) {
		List<Trade> trades = new ArrayList<>();

		TradingRecord buyingRecord = new TradingRecord(OrderType.BUY);
		TradingRecord sellingRecord = new TradingRecord(OrderType.SELL);

		boolean bought = false;
		boolean sold = false;

		for (int i = series.getBegin(); i < series.getEnd(); i++) {
			boolean canOpen = param.canOpenPosition(series.getTick(i));
			boolean forceClose = param.forceClosePosition(series.getTick(i));

			boolean buyOperate = buyStrategy.shouldOperate(i, buyingRecord);
			boolean sellOperate = sellStrategy.shouldOperate(i, sellingRecord);

			buyOperate = buyOperate && (canOpen || bought);
			sellOperate = sellOperate && (canOpen || sold);

			buyOperate = buyOperate || (forceClose && bought);
			sellOperate = sellOperate || (forceClose && sold);

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
				if (sold)
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
