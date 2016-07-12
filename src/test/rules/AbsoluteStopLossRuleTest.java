package test.rules;

import org.junit.Assert;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.rules.AbsoluteStopLossRule;
import test.AbstractTest;
import test.helpers.PriceSeries;
import test.helpers.PriceType;
import test.helpers.TimeSeriesHelper;

public class AbsoluteStopLossRuleTest extends AbstractTest {

	@Test
	public void testRuleInABuyingTrade() {
		// Arrange
		int[] lowPrices = { 50, 51, 49, 52, 50, 52, 48, 42, 42, 37, 35, 33, 37, 45, 49 };
		int[] closePrices = { 50, 51, 49, 52, 51, 52, 49, 43, 42, 41, 38, 35, 41, 51, 50 };

		PriceSeries lowSeries = new PriceSeries(PriceType.LOW, lowPrices);
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(lowSeries, closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(7));

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(7, Decimal.valueOf(43), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(10, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(36), stopLoss.getExitPrice(tradingRecord));
		tradingRecord.operate(10, Decimal.valueOf(36), Decimal.ONE);

		Assert.assertFalse(stopLoss.isSatisfied(11, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}

	@Test
	public void testRuleInABuyingTradeAchievingTheStopLossExactlyInTheClosingPrice() {
		// Arrange
		int[] lowPrices = { 50, 51, 49, 52, 50, 52, 48, 42, 42, 37, 35, 33, 37, 45, 49 };
		int[] closePrices = { 50, 51, 49, 52, 51, 52, 49, 43, 42, 41, 38, 35, 41, 51, 50 };

		PriceSeries lowSeries = new PriceSeries(PriceType.LOW, lowPrices);
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(lowSeries, closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(8));

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(7, Decimal.valueOf(43), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(10, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(35), stopLoss.getExitPrice(tradingRecord));
		tradingRecord.operate(10, Decimal.valueOf(35), Decimal.ONE);

		Assert.assertFalse(stopLoss.isSatisfied(11, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}

	@Test
	public void testRuleInASellingTrade() {
		// Arrange
		int[] highPrices = { 50, 49, 51, 48, 50, 48, 52, 58, 58, 63, 65, 67, 63, 55, 51 };
		int[] closePrices = { 50, 49, 51, 48, 49, 48, 51, 57, 58, 59, 62, 65, 59, 49, 50 };

		PriceSeries highSeries = new PriceSeries(PriceType.HIGH, highPrices);
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(highSeries, closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(7));

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(7, Decimal.valueOf(57), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(10, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(64), stopLoss.getExitPrice(tradingRecord));
		tradingRecord.operate(10, Decimal.valueOf(64), Decimal.ONE);

		Assert.assertFalse(stopLoss.isSatisfied(11, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}

	@Test
	public void testRuleInASellingTradeAchievingTheStopLossExactlyInTheClosingPrice() {
		// Arrange
		int[] highPrices = { 50, 49, 51, 48, 50, 48, 52, 58, 58, 63, 65, 67, 63, 55, 51 };
		int[] closePrices = { 50, 49, 51, 48, 49, 48, 51, 57, 58, 59, 62, 65, 59, 49, 50 };

		PriceSeries highSeries = new PriceSeries(PriceType.HIGH, highPrices);
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(highSeries, closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(8));

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(7, Decimal.valueOf(57), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(10, tradingRecord));
		Assert.assertEquals(Decimal.valueOf(65), stopLoss.getExitPrice(tradingRecord));
		tradingRecord.operate(10, Decimal.valueOf(65), Decimal.ONE);

		Assert.assertFalse(stopLoss.isSatisfied(11, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}
}
