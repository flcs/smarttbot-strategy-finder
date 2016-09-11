package strategy;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import core.enums.StopType;
import core.parameters.RobotParameters;
import core.parameters.entry.EntryParameters;
import core.parameters.entry.RSIParameters;
import core.parameters.exit.ExitParameters;
import core.parameters.exit.TrailingStopGainParameters;
import core.strategy.RobotStrategy;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import helpers.AbstractTest;
import helpers.PriceSeries;
import helpers.PriceType;
import helpers.TimeSeriesHelper;

public class StopTrailingGainTest extends AbstractTest {

	private static TimeSeries buyingSeries;
	private static TimeSeries sellingSeries;
	private static EntryParameters entryParameters;

	@BeforeClass
	public static void setupTimeSeries() {
		int[] buyingClosePrices = { 20, 21, 19, 21, 17, 11, 15, 18, 16, 19, 16, 21, 17, 20 };
		PriceSeries buyingCloseSeries = new PriceSeries(PriceType.CLOSE, buyingClosePrices);
		buyingSeries = TimeSeriesHelper.getTimeSeries(buyingCloseSeries);

		int[] sellingClosePrices = { 20, 19, 21, 19, 23, 29, 25, 22, 24, 21, 24, 19, 23, 20 };
		PriceSeries sellingCloseSeries = new PriceSeries(PriceType.CLOSE, sellingClosePrices);
		sellingSeries = TimeSeriesHelper.getTimeSeries(sellingCloseSeries);

		RSIParameters rsiParam = new RSIParameters(3, 30, 70);
		entryParameters = new EntryParameters(null, rsiParam, null);
	}

	@Test
	public void trailingPercentageStopGainInABuyingTrade() {
		// Arrange
		TrailingStopGainParameters stop = new TrailingStopGainParameters(StopType.PERCENTAGE, Decimal.valueOf(11),
				Decimal.valueOf(19));
		ExitParameters exitParameters = new ExitParameters(null, null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(buyingSeries, parameters);

		// Assert
		Trade trade = trades.get(1);
		Assert.assertEquals(4, trade.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(17), trade.getEntry().getPrice());

		Assert.assertEquals(12, trade.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade.getExit().getType());
		Assert.assertEquals(Decimal.valueOf("17.01"), trade.getExit().getPrice());
	}

	@Test
	public void trailingPercentageStopGainInASellingTrade() {
		// Arrange
		TrailingStopGainParameters stop = new TrailingStopGainParameters(StopType.PERCENTAGE, Decimal.valueOf(8),
				Decimal.valueOf(21));
		ExitParameters exitParameters = new ExitParameters(null, null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(sellingSeries, parameters);

		// Assert
		Trade trade = trades.get(2);
		Assert.assertEquals(4, trade.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(23), trade.getEntry().getPrice());

		Assert.assertEquals(12, trade.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade.getExit().getType());
		Assert.assertEquals(Decimal.valueOf("22.99"), trade.getExit().getPrice());
	}

	@Test
	public void trailingAbsoluteStopGainInABuyingTrade() {
		// Arrange
		TrailingStopGainParameters stop = new TrailingStopGainParameters(StopType.ABSOLUTE, Decimal.TWO,
				Decimal.valueOf(4));
		ExitParameters exitParameters = new ExitParameters(null, null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(buyingSeries, parameters);

		// Assert
		Trade trade = trades.get(1);
		Assert.assertEquals(4, trade.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(17), trade.getEntry().getPrice());

		Assert.assertEquals(12, trade.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(17), trade.getExit().getPrice());
	}

	@Test
	public void trailingAbsoluteStopGainInASellingTrade() {
		// Arrange
		TrailingStopGainParameters stop = new TrailingStopGainParameters(StopType.ABSOLUTE, Decimal.TWO,
				Decimal.valueOf(4));
		ExitParameters exitParameters = new ExitParameters(null, null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(sellingSeries, parameters);

		// Assert
		Trade trade = trades.get(2);
		Assert.assertEquals(4, trade.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(23), trade.getEntry().getPrice());

		Assert.assertEquals(12, trade.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(23), trade.getExit().getPrice());
	}
}