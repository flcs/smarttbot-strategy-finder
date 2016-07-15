package test.rules;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.enums.StopType;
import main.core.rules.stops.FixedStopGainRule;
import main.core.rules.stops.FixedStopLossRule;
import test.AbstractTest;
import test.helpers.PriceSeries;
import test.helpers.PriceType;
import test.helpers.TimeSeriesHelper;

public class FixedStopRuleTest extends AbstractTest {

	private static ClosePriceIndicator closePriceIndicator;

	@BeforeClass
	public static void setupTimeSeries() {
		int[] highPrices = { 52, 54, 51, 55, 59, 54 };
		int[] lowPrices = { 48, 46, 49, 45, 41, 46 };
		int[] closePrices = { 51, 50, 51, 49, 51, 53 };

		PriceSeries highSeries = new PriceSeries(PriceType.HIGH, highPrices);
		PriceSeries lowSeries = new PriceSeries(PriceType.LOW, lowPrices);
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);

		TimeSeries series = TimeSeriesHelper.getTimeSeries(highSeries, lowSeries, closeSeries);
		closePriceIndicator = new ClosePriceIndicator(series);
	}

	@Test
	public void stopLossInABuyingTrade() {
		// Arrange
		FixedStopLossRule stop = new FixedStopLossRule(closePriceIndicator, Decimal.valueOf(8), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(4, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(42), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(4, Decimal.valueOf(42), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void stopLossInABuyingTradeAchievingTheStopLossExactlyAtLowPrice() {
		// Arrange
		FixedStopLossRule stop = new FixedStopLossRule(closePriceIndicator, Decimal.valueOf(5), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(45), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(45), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void stopLossInASellingTrade() {
		// Arrange
		FixedStopLossRule stop = new FixedStopLossRule(closePriceIndicator, Decimal.valueOf(8), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(4, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(58), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(4, Decimal.valueOf(58), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void stopLossInASellingTradeAchievingTheStopLossExactlyAtHighPrice() {
		// Arrange
		FixedStopLossRule stop = new FixedStopLossRule(closePriceIndicator, Decimal.valueOf(5), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(55), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(55), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void percentageStopLossInABuyingTrade() {
		// Arrange
		FixedStopLossRule stop = new FixedStopLossRule(closePriceIndicator, Decimal.valueOf(10), StopType.PERCENTAGE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(45), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(45), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void percentageStopLossInASellingTrade() {
		// Arrange
		FixedStopLossRule stop = new FixedStopLossRule(closePriceIndicator, Decimal.valueOf(10), StopType.PERCENTAGE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(55), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(55), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void stopGainInABuyingTrade() {
		// Arrange
		FixedStopGainRule stop = new FixedStopGainRule(closePriceIndicator, Decimal.valueOf(8), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(4, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(58), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(4, Decimal.valueOf(58), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void stopGainInABuyingTradeAchievingTheStopLossExactlyAtHighPrice() {
		// Arrange
		FixedStopGainRule stop = new FixedStopGainRule(closePriceIndicator, Decimal.valueOf(5), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(55), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(55), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void stopGainInASellingTrade() {
		// Arrange
		FixedStopGainRule stop = new FixedStopGainRule(closePriceIndicator, Decimal.valueOf(8), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(4, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(42), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(4, Decimal.valueOf(42), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void stopGainInASellingTradeAchievingTheStopLossExactlyAtLowPrice() {
		// Arrange
		FixedStopGainRule stop = new FixedStopGainRule(closePriceIndicator, Decimal.valueOf(5), StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(45), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(45), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void percentageStopGainInABuyingTrade() {
		// Arrange
		FixedStopGainRule stop = new FixedStopGainRule(closePriceIndicator, Decimal.valueOf(10), StopType.PERCENTAGE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(55), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(55), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

	@Test
	public void percentageStopGainInASellingTrade() {
		// Arrange
		FixedStopGainRule stop = new FixedStopGainRule(closePriceIndicator, Decimal.valueOf(10), StopType.PERCENTAGE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(3, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(45), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(3, Decimal.valueOf(45), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
	}

}
