package rules;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import core.enums.StopType;
import core.rules.stops.TrailingStopRule;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import helpers.AbstractTest;
import helpers.PriceSeries;
import helpers.PriceType;
import helpers.TimeSeriesHelper;

public class TrailingStopRuleTest extends AbstractTest {

	private static ClosePriceIndicator buyingIndicator;
	private static ClosePriceIndicator sellingIndicator;

	@BeforeClass
	public static void setupTimeSeries() {
		int[] buyingClosePrices = { 49, 50, 55, 43, 56, 55, 58, 56, 53, 43 };
		PriceSeries buyingCloseSeries = new PriceSeries(PriceType.CLOSE, buyingClosePrices);
		TimeSeries buyingSeries = TimeSeriesHelper.getTimeSeries(buyingCloseSeries);
		buyingIndicator = new ClosePriceIndicator(buyingSeries);

		int[] sellingClosePrices = { 51, 50, 45, 57, 44, 45, 42, 44, 47, 57 };
		PriceSeries sellingCloseSeries = new PriceSeries(PriceType.CLOSE, sellingClosePrices);
		TimeSeries sellingSeries = TimeSeriesHelper.getTimeSeries(sellingCloseSeries);
		sellingIndicator = new ClosePriceIndicator(sellingSeries);
	}

	@Test
	public void stopABuyingTrade() {
		// Arrange
		TrailingStopRule stop = new TrailingStopRule(buyingIndicator, Decimal.valueOf(6), Decimal.valueOf(4),
				StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(6, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(7, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(8, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(54), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(8, Decimal.valueOf(54), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(9, tradingRecord));
	}

	@Test
	public void stopABuyingExactlyAtLowPrice() {
		// Arrange
		TrailingStopRule stop = new TrailingStopRule(buyingIndicator, Decimal.valueOf(6), Decimal.valueOf(2),
				StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(6, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(7, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(56), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(7, Decimal.valueOf(56), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(9, tradingRecord));
	}

	@Test
	public void stopASellingTrade() {
		// Arrange
		TrailingStopRule stop = new TrailingStopRule(sellingIndicator, Decimal.valueOf(6), Decimal.valueOf(4),
				StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(6, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(7, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(8, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(46), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(8, Decimal.valueOf(46), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(9, tradingRecord));
	}

	@Test
	public void stopASellingTradeExactlyAtHighPrice() {
		// Arrange
		TrailingStopRule stop = new TrailingStopRule(sellingIndicator, Decimal.valueOf(6), Decimal.valueOf(2),
				StopType.ABSOLUTE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(6, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(7, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(44), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(7, Decimal.valueOf(44), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(9, tradingRecord));
	}

	@Test
	public void percentageStopInABuyingTrade() {
		// Arrange
		TrailingStopRule stop = new TrailingStopRule(buyingIndicator, Decimal.valueOf(12), Decimal.valueOf(7),
				StopType.PERCENTAGE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(6, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(7, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(8, tradingRecord));
		Assert.assertEquals(Decimal.valueOf("53.94"), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(8, Decimal.valueOf("53.94"), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(9, tradingRecord));
	}

	@Test
	public void percentageStopInASellingTrade() {
		// Arrange
		TrailingStopRule stop = new TrailingStopRule(sellingIndicator, Decimal.valueOf(12), Decimal.valueOf(10),
				StopType.PERCENTAGE);

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(1, Decimal.valueOf(50), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stop.isSatisfied(0, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(1, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(2, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(3, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(4, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(5, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(6, tradingRecord));
		Assert.assertFalse(stop.isSatisfied(7, tradingRecord));

		Assert.assertTrue(stop.isSatisfied(8, tradingRecord));
		Assert.assertEquals(Decimal.valueOf("46.2"), stop.getExitPrice(tradingRecord));
		tradingRecord.operate(8, Decimal.valueOf("46.2"), Decimal.ONE);

		Assert.assertFalse(stop.isSatisfied(9, tradingRecord));
	}

}
