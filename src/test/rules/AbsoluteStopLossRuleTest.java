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
		int[] closePrices = { 50, 51, 49, 52, 51, 52, 49, 43, 42, 41, 38, 35, 41, 51, 50 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(6));

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(7, Decimal.valueOf(43), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(10, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(11, tradingRecord));

		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}

	@Test
	public void testRuleInABuyingTradeAchievingTheStopLossExactlyInTheClosingPrice() {
		// Arrange
		int[] closePrices = { 50, 51, 49, 52, 51, 52, 49, 43, 42, 41, 38, 35, 41, 51, 50 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(5));

		TradingRecord tradingRecord = new TradingRecord(OrderType.BUY);
		tradingRecord.operate(7, Decimal.valueOf(43), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(10, tradingRecord));
		Assert.assertTrue(stopLoss.isSatisfied(11, tradingRecord));

		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}

	@Test
	public void testRuleInASellingTrade() {
		// Arrange
		int[] closePrices = { 50, 49, 51, 48, 49, 48, 51, 57, 58, 59, 62, 65, 59, 49, 50 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(6));

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(7, Decimal.valueOf(57), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(10, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(11, tradingRecord));

		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}

	@Test
	public void testRuleInASellingTradeAchievingTheStopLossExactlyInTheClosingPrice() {
		// Arrange
		int[] closePrices = { 50, 49, 51, 48, 49, 48, 51, 57, 58, 59, 62, 65, 59, 49, 50 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closeSeries);
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);

		AbsoluteStopLossRule stopLoss = new AbsoluteStopLossRule(closePriceIndicator, Decimal.valueOf(5));

		TradingRecord tradingRecord = new TradingRecord(OrderType.SELL);
		tradingRecord.operate(7, Decimal.valueOf(57), Decimal.ONE);

		// Act & Assert
		Assert.assertFalse(stopLoss.isSatisfied(8, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(9, tradingRecord));

		Assert.assertTrue(stopLoss.isSatisfied(10, tradingRecord));
		Assert.assertTrue(stopLoss.isSatisfied(11, tradingRecord));

		Assert.assertFalse(stopLoss.isSatisfied(12, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(13, tradingRecord));
		Assert.assertFalse(stopLoss.isSatisfied(14, tradingRecord));
	}
}
