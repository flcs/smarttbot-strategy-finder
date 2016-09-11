package strategy;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import core.enums.StopType;
import core.parameters.RobotParameters;
import core.parameters.entry.BollingerBandsParameters;
import core.parameters.entry.EntryParameters;
import core.parameters.exit.ExitParameters;
import core.parameters.exit.FixedStopGainParameters;
import core.strategy.RobotStrategy;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import helpers.AbstractTest;
import helpers.PriceSeries;
import helpers.PriceType;
import helpers.TimeSeriesHelper;

public class StopGainTest extends AbstractTest {

	private static TimeSeries buyingSeries;
	private static TimeSeries sellingSeries;
	private static EntryParameters entryParameters;

	@BeforeClass
	public static void setupTimeSeries() {
		int[] highPrices = { 50, 49, 51, 48, 50, 48, 52, 58, 58, 63, 65, 67, 63, 55, 51 };
		int[] buyingClosePrices = { 50, 51, 49, 52, 51, 52, 49, 43, 42, 41, 38, 35, 41, 51, 50 };

		PriceSeries highSeries = new PriceSeries(PriceType.HIGH, highPrices);
		PriceSeries buyingCloseSeries = new PriceSeries(PriceType.CLOSE, buyingClosePrices);
		buyingSeries = TimeSeriesHelper.getTimeSeries(highSeries, buyingCloseSeries);

		int[] lowPrices = { 50, 51, 49, 52, 50, 52, 48, 42, 42, 37, 35, 33, 37, 45, 49 };
		int[] sellingClosePrices = { 50, 49, 51, 48, 49, 48, 51, 57, 58, 59, 62, 65, 59, 49, 50 };

		PriceSeries lowSeries = new PriceSeries(PriceType.LOW, lowPrices);
		PriceSeries sellingCloseSeries = new PriceSeries(PriceType.CLOSE, sellingClosePrices);
		sellingSeries = TimeSeriesHelper.getTimeSeries(lowSeries, sellingCloseSeries);

		BollingerBandsParameters bbParam = new BollingerBandsParameters(7, Decimal.TWO);
		entryParameters = new EntryParameters(null, null, bbParam);
	}

	@Test
	public void fixedPercentageStopGainInABuyingTrade() {
		// Arrange
		FixedStopGainParameters stop = new FixedStopGainParameters(StopType.PERCENTAGE, Decimal.valueOf(49));
		ExitParameters exitParameters = new ExitParameters(null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(buyingSeries, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(7, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(43), trade1.getEntry().getPrice());

		Assert.assertEquals(10, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf("64.07"), trade1.getExit().getPrice());
	}

	@Test
	public void fixedPercentageStopGainInASellingTrade() {
		// Arrange
		FixedStopGainParameters stop = new FixedStopGainParameters(StopType.PERCENTAGE, Decimal.valueOf(37));
		ExitParameters exitParameters = new ExitParameters(null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(sellingSeries, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(7, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(57), trade1.getEntry().getPrice());

		Assert.assertEquals(10, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf("35.91"), trade1.getExit().getPrice());
	}

	@Test
	public void fixedAbsoluteStopGainInABuyingTrade() {
		// Arrange
		FixedStopGainParameters stop = new FixedStopGainParameters(StopType.ABSOLUTE, Decimal.valueOf(21));
		ExitParameters exitParameters = new ExitParameters(null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(buyingSeries, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(7, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(43), trade1.getEntry().getPrice());

		Assert.assertEquals(10, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(64), trade1.getExit().getPrice());
	}

	@Test
	public void fixedAbsoluteStopGainInASellingTrade() {
		// Arrange
		FixedStopGainParameters stop = new FixedStopGainParameters(StopType.ABSOLUTE, Decimal.valueOf(21));
		ExitParameters exitParameters = new ExitParameters(null, null, stop);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(sellingSeries, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(7, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(57), trade1.getEntry().getPrice());

		Assert.assertEquals(10, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(36), trade1.getExit().getPrice());
	}
}