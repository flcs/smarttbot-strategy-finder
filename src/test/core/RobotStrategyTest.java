package test.core;

import java.time.LocalTime;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import main.core.parameter.BollingerBandsParameters;
import main.core.parameter.DayTradeParameters;
import main.core.parameter.EntryParameters;
import main.core.parameter.MovingAverageParameters;
import main.core.parameter.RSIParameters;
import main.core.parameter.RobotParameters;
import main.core.strategy.RobotStrategy;

public class RobotStrategyTest extends AbstractTest {

	@Test
	public void backtestSimpleMovingAverage() {
		// Arrange
		int[] closingPrices = { 15, 16, 30, 29, 13, 16, 17, 18, 25, 24, 23, 2, 3, 8 };
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices);

		MovingAverageParameters movingAverage = new MovingAverageParameters(3, 6);
		EntryParameters entryParameters = new EntryParameters(movingAverage, null, null);
		RobotParameters parameters = new RobotParameters(entryParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(3, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(29), trade1.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getEntry().getAmount());

		Assert.assertEquals(5, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(16), trade1.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getExit().getAmount());

		Trade trade2 = trades.get(1);
		Assert.assertEquals(5, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(16), trade2.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getEntry().getAmount());

		Assert.assertEquals(8, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(25), trade2.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getExit().getAmount());

		Trade trade3 = trades.get(2);
		Assert.assertEquals(8, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(25), trade3.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getEntry().getAmount());

		Assert.assertEquals(11, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(2), trade3.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getExit().getAmount());

		Trade trade4 = trades.get(3);
		Assert.assertEquals(11, trade4.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade4.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(2), trade4.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getEntry().getAmount());

		Assert.assertEquals(13, trade4.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade4.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(8), trade4.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getExit().getAmount());
	}

	@Test
	public void backtestRSI() {
		// Arrange
		int[] closingPrices = { 15, 16, 31, 24, 3, 16, 17, 17, 21, 24, 23, 19, 16, 20 };
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices);

		RSIParameters rsi = new RSIParameters(3, 30, 70);
		EntryParameters entryParameters = new EntryParameters(null, rsi, null);
		RobotParameters parameters = new RobotParameters(entryParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(0, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(15), trade1.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getEntry().getAmount());

		Assert.assertEquals(4, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(3), trade1.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getExit().getAmount());

		Trade trade2 = trades.get(1);
		Assert.assertEquals(4, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(3), trade2.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getEntry().getAmount());

		Assert.assertEquals(9, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(24), trade2.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getExit().getAmount());

		Trade trade3 = trades.get(2);
		Assert.assertEquals(9, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(24), trade3.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getEntry().getAmount());

		Assert.assertEquals(12, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(16), trade3.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getExit().getAmount());

		Trade trade4 = trades.get(3);
		Assert.assertEquals(12, trade4.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade4.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(16), trade4.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getEntry().getAmount());

		Assert.assertEquals(13, trade4.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade4.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(20), trade4.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getExit().getAmount());
	}

	@Test
	public void backtestBollingerBands() {
		// Arrange
		int[] closingPrices = { 10, 12, 8, 11, 9, 19, 20, 15, 17, 13, 17, 8, 10, 27, 15 };
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices);

		BollingerBandsParameters bb = new BollingerBandsParameters(7, Decimal.valueOf(2));
		EntryParameters entryParameters = new EntryParameters(null, null, bb);
		RobotParameters parameters = new RobotParameters(entryParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(5, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(19), trade1.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getEntry().getAmount());

		Assert.assertEquals(11, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(8), trade1.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getExit().getAmount());

		Trade trade2 = trades.get(1);
		Assert.assertEquals(11, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(8), trade2.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getEntry().getAmount());

		Assert.assertEquals(13, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(27), trade2.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getExit().getAmount());

		Trade trade3 = trades.get(2);
		Assert.assertEquals(13, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(27), trade3.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getEntry().getAmount());

		Assert.assertEquals(14, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(15), trade3.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getExit().getAmount());
	}

	@Test
	public void backtestUsingAllIndicators() {
		// Arrange
		int[] closingPrices = { 50, 49, 50, 51, 49, 46, 40, 45, 63, 20, 45, 43, 42, 40, 45 };
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices);

		MovingAverageParameters movingAverage = new MovingAverageParameters(3, 6);
		RSIParameters rsi = new RSIParameters(3, 30, 70);
		BollingerBandsParameters bb = new BollingerBandsParameters(7, Decimal.valueOf(2));
		EntryParameters entryParameters = new EntryParameters(movingAverage, rsi, bb);
		RobotParameters parameters = new RobotParameters(entryParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(5, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(46), trade1.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getEntry().getAmount());

		Assert.assertEquals(8, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade1.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getExit().getAmount());

		Trade trade2 = trades.get(1);
		Assert.assertEquals(8, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade2.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getEntry().getAmount());

		Assert.assertEquals(9, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(20), trade2.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getExit().getAmount());

		Trade trade3 = trades.get(2);
		Assert.assertEquals(9, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(20), trade3.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getEntry().getAmount());

		Assert.assertEquals(12, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(42), trade3.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getExit().getAmount());
	}

	@Test
	public void backtestExitTimeLimit() {
		// Arrange
		int[] closingPrices = { 50, 50, 51, 49, 52, 52, 53, 50 };
		DateTime seriesStartingTime = new DateTime(2016, 7, 1, 17, 25);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices, seriesStartingTime);

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
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices);

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
	}

	@Test
	public void backtestFinalEntryTimeLimit() {
		// Arrange
		int[] closingPrices = { 20, 17, 22, 21, 19, 20, 21, 20 };
		DateTime seriesStartingTime = new DateTime(2016, 7, 1, 16, 35);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices, seriesStartingTime);

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
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices, seriesStartingTime);

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
	}
}
