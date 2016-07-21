package test.strategy;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import main.core.enums.StopType;
import main.core.parameters.RobotParameters;
import main.core.parameters.entry.BollingerBandsParameters;
import main.core.parameters.entry.EntryParameters;
import main.core.parameters.exit.ExitParameters;
import main.core.parameters.exit.FixedStopLossParameters;
import main.core.strategy.RobotStrategy;
import test.AbstractTest;
import test.helpers.PriceSeries;
import test.helpers.PriceType;
import test.helpers.TimeSeriesHelper;

public class StopLossTest extends AbstractTest {

	private static TimeSeries buyingSeries;
	private static TimeSeries sellingSeries;
	private static EntryParameters entryParameters;

	@BeforeClass
	public static void setupTimeSeries() {
		int[] lowPrices = { 50, 51, 49, 52, 50, 52, 48, 42, 42, 37, 35, 33, 37, 45, 49 };
		int[] buyingClosePrices = { 50, 51, 49, 52, 51, 52, 49, 43, 42, 41, 38, 35, 41, 51, 50 };

		PriceSeries lowSeries = new PriceSeries(PriceType.LOW, lowPrices);
		PriceSeries buyingCloseSeries = new PriceSeries(PriceType.CLOSE, buyingClosePrices);
		buyingSeries = TimeSeriesHelper.getTimeSeries(lowSeries, buyingCloseSeries);

		int[] highPrices = { 50, 49, 51, 48, 50, 48, 52, 58, 58, 63, 65, 67, 63, 55, 51 };
		int[] sellingClosePrices = { 50, 49, 51, 48, 49, 48, 51, 57, 58, 59, 62, 65, 59, 49, 50 };

		PriceSeries highSeries = new PriceSeries(PriceType.HIGH, highPrices);
		PriceSeries sellingCloseSeries = new PriceSeries(PriceType.CLOSE, sellingClosePrices);
		sellingSeries = TimeSeriesHelper.getTimeSeries(highSeries, sellingCloseSeries);

		BollingerBandsParameters bbParam = new BollingerBandsParameters(7, Decimal.TWO);
		entryParameters = new EntryParameters(null, null, bbParam);
	}

	@Test
	public void fixedPercentageStopLossInABuyingTrade() {
		// Arrange
		FixedStopLossParameters stop = new FixedStopLossParameters(StopType.PERCENTAGE, Decimal.valueOf(16));
		ExitParameters exitParameters = new ExitParameters(stop, null);
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
		Assert.assertEquals(Decimal.valueOf("36.12"), trade1.getExit().getPrice());
	}

	@Test
	public void fixedPercentageStopLossInASellingTrade() {
		// Arrange
		FixedStopLossParameters stop = new FixedStopLossParameters(StopType.PERCENTAGE, Decimal.valueOf(12));
		ExitParameters exitParameters = new ExitParameters(stop, null);
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
		Assert.assertEquals(Decimal.valueOf("63.84"), trade1.getExit().getPrice());
	}

	@Test
	public void fixedAbsoluteStopLossInABuyingTrade() {
		// Arrange
		FixedStopLossParameters stop = new FixedStopLossParameters(StopType.ABSOLUTE, Decimal.valueOf(7));
		ExitParameters exitParameters = new ExitParameters(stop, null);
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
		Assert.assertEquals(Decimal.valueOf(36), trade1.getExit().getPrice());
	}

	@Test
	public void fixedAbsoluteStopLossInASellingTrade() {
		// Arrange
		FixedStopLossParameters stop = new FixedStopLossParameters(StopType.ABSOLUTE, Decimal.valueOf(7));
		ExitParameters exitParameters = new ExitParameters(stop, null);
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
		Assert.assertEquals(Decimal.valueOf(64), trade1.getExit().getPrice());
	}

}