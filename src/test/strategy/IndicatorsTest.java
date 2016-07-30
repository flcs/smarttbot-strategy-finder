package test.strategy;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import main.core.enums.MovingAverageType;
import main.core.parameters.RobotParameters;
import main.core.parameters.entry.BollingerBandsParameters;
import main.core.parameters.entry.EntryParameters;
import main.core.parameters.entry.MovingAverageParameters;
import main.core.parameters.entry.RSIParameters;
import main.core.strategy.RobotStrategy;
import test.AbstractTest;
import test.helpers.PriceSeries;
import test.helpers.PriceType;
import test.helpers.TimeSeriesHelper;

public class IndicatorsTest extends AbstractTest {

	private static TimeSeries movingAverageSeries;

	@BeforeClass
	public static void setupTimeSeries() {
		int[] closePrices = { 50, 50, 50, 51, 47, 63, 45, 45, 70, 50 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		movingAverageSeries = TimeSeriesHelper.getTimeSeries(closeSeries);
	}

	@Test
	public void backtestSimpleMovingAverage() {
		// Arrange
		MovingAverageParameters movingAverage = new MovingAverageParameters(3, 6);
		EntryParameters entryParameters = new EntryParameters(movingAverage, null, null);
		RobotParameters parameters = new RobotParameters(entryParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(movingAverageSeries, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(3, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(51), trade1.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getEntry().getAmount());

		Assert.assertEquals(4, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(47), trade1.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getExit().getAmount());

		Trade trade2 = trades.get(1);
		Assert.assertEquals(4, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(47), trade2.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getEntry().getAmount());

		Assert.assertEquals(5, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade2.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getExit().getAmount());

		Trade trade3 = trades.get(2);
		Assert.assertEquals(5, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade3.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getEntry().getAmount());

		Assert.assertEquals(8, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(70), trade3.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getExit().getAmount());

		Trade trade4 = trades.get(3);
		Assert.assertEquals(8, trade4.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade4.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(70), trade4.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getEntry().getAmount());

		Assert.assertEquals(9, trade4.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade4.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(50), trade4.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getExit().getAmount());

		Assert.assertEquals(4, trades.size());
	}

	@Test
	public void backtestExponencialMovingAverage() {
		// Arrange
		MovingAverageParameters movingAverage = new MovingAverageParameters(MovingAverageType.EXPONENTIAL, 3, 6);
		EntryParameters entryParameters = new EntryParameters(movingAverage, null, null);
		RobotParameters parameters = new RobotParameters(entryParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(movingAverageSeries, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(3, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(51), trade1.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getEntry().getAmount());

		Assert.assertEquals(4, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(47), trade1.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getExit().getAmount());

		Trade trade2 = trades.get(1);
		Assert.assertEquals(4, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(47), trade2.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getEntry().getAmount());

		Assert.assertEquals(5, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade2.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getExit().getAmount());

		Trade trade3 = trades.get(2);
		Assert.assertEquals(5, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade3.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getEntry().getAmount());

		Assert.assertEquals(7, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(45), trade3.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getExit().getAmount());

		Trade trade4 = trades.get(3);
		Assert.assertEquals(7, trade4.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade4.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(45), trade4.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getEntry().getAmount());

		Assert.assertEquals(8, trade4.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade4.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(70), trade4.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade4.getExit().getAmount());

		Trade trade5 = trades.get(4);
		Assert.assertEquals(8, trade5.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade5.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(70), trade5.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade5.getEntry().getAmount());

		Assert.assertEquals(9, trade5.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade5.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(50), trade5.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade5.getExit().getAmount());

		Assert.assertEquals(5, trades.size());
	}

	@Test
	public void backtestRSI() {
		// Arrange
		int[] closingPrices = { 15, 16, 31, 24, 3, 16, 17, 17, 21, 24, 23, 19, 16, 20 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);

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

		Assert.assertEquals(4, trades.size());
	}

	@Test
	public void backtestBollingerBands() {
		// Arrange
		int[] closingPrices = { 10, 12, 8, 11, 9, 19, 20, 15, 17, 13, 17, 8, 10, 27, 15 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);

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

		Assert.assertEquals(3, trades.size());
	}

	@Test
	public void backtestUsingAllIndicators() {
		// Arrange
		int[] closingPrices = { 50, 49, 50, 51, 49, 46, 40, 45, 63, 20, 45, 43, 42, 40, 45 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);

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

		Assert.assertEquals(3, trades.size());
	}

}
