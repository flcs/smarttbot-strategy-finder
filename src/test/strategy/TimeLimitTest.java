package test.strategy;

import java.time.LocalTime;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import main.core.parameters.RobotParameters;
import main.core.parameters.daytrade.DayTradeParameters;
import main.core.parameters.entry.EntryParameters;
import main.core.parameters.entry.MovingAverageParameters;
import main.core.parameters.entry.RSIParameters;
import main.core.strategy.RobotStrategy;
import test.AbstractTest;
import test.helpers.PriceSeries;
import test.helpers.PriceType;
import test.helpers.TimeSeriesHelper;

public class TimeLimitTest extends AbstractTest {

	@Test
	public void backtestExitTimeLimit() {
		// Arrange
		int[] closingPrices = { 50, 50, 51, 49, 52, 52, 53, 50 };
		DateTime seriesStartingTime = new DateTime(2016, 7, 1, 17, 25);

		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(seriesStartingTime, closeSeries);

		RSIParameters rsi = new RSIParameters(3, 30, 70);
		EntryParameters entryParameters = new EntryParameters(null, rsi, null);
		DayTradeParameters dayTradeParameters = new DayTradeParameters(null, null, LocalTime.of(17, 30));
		RobotParameters parameters = new RobotParameters(entryParameters, dayTradeParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(0, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());

		Assert.assertEquals(4, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
	}

	@Test
	public void backtestInitialEntryTimeLimit() {
		// Arrange
		int[] closingPrices = { 50, 49, 51, 47, 17, 17, 17, 16 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);

		RSIParameters rsi = new RSIParameters(3, 30, 70);
		EntryParameters entryParameters = new EntryParameters(null, rsi, null);
		DayTradeParameters dayTradeParameters = new DayTradeParameters(LocalTime.of(9, 7), null, null);
		RobotParameters parameters = new RobotParameters(entryParameters, dayTradeParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(6, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getEntry().getType());

		Assert.assertEquals(7, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getExit().getType());

		Assert.assertEquals(1, trades.size());
	}

	@Test
	public void backtestFinalEntryTimeLimit() {
		// Arrange
		int[] closingPrices = { 20, 17, 22, 21, 19, 20, 21, 20 };
		DateTime seriesStartingTime = new DateTime(2016, 7, 1, 16, 35);

		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(seriesStartingTime, closeSeries);

		MovingAverageParameters movingAverage = new MovingAverageParameters(3, 6);
		EntryParameters entryParameters = new EntryParameters(movingAverage, null, null);
		DayTradeParameters dayTradeParameters = new DayTradeParameters(null, LocalTime.of(16, 40), null);
		RobotParameters parameters = new RobotParameters(entryParameters, dayTradeParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Assert.assertTrue(trades.isEmpty());
	}

	@Test
	public void backtestClosePositionBetweenEntryAndExitTimeLimits() {
		// Arrange
		int[] closingPrices = { 42, 41, 40, 46, 45, 40, 38, 39, 34, 35, 31 };
		DateTime seriesStartingTime = new DateTime(2016, 7, 1, 16, 35);

		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(seriesStartingTime, closeSeries);

		MovingAverageParameters movingAverage = new MovingAverageParameters(3, 6);
		EntryParameters entryParameters = new EntryParameters(movingAverage, null, null);
		DayTradeParameters dayTradeParameters = new DayTradeParameters(null, LocalTime.of(16, 40),
				LocalTime.of(16, 45));
		RobotParameters parameters = new RobotParameters(entryParameters, dayTradeParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(3, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());

		Assert.assertEquals(6, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());

		Assert.assertEquals(1, trades.size());
	}

}
