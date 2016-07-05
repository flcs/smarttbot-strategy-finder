package test.core;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.indicator.WilderRSIIndicator;

public class WilderRSIIndicatorTest extends AbstractTest {
	private static TimeSeries series;

	@BeforeClass
	public static void setupClosingPrices() {
		int[] closingPrices = { 20, 15, 14, 18, 16, 11, 15, 7, 6, 15, 14, 19, 10, 1 };
		series = TimeSeriesHelper.getTimeSeries(closingPrices);
	}

	@Test
	public void getValueWhenIndexIsZeroAndTimeFrameIsThree() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderRSIIndicator rsiIndicator = new WilderRSIIndicator(closePriceIndicator, 3);

		// Act
		Decimal rsi = rsiIndicator.getValue(0);

		// Assert
		Assert.assertEquals(Decimal.HUNDRED, rsi);
	}

	@Test
	public void getValueWhenIndexIsTwoAndTimeFrameIsThree() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderRSIIndicator rsiIndicator = new WilderRSIIndicator(closePriceIndicator, 3);

		// Act
		Decimal rsi = rsiIndicator.getValue(2);

		// Assert
		Assert.assertEquals(Decimal.ZERO, rsi);
	}

	@Test
	public void getValueWhenIndexIsThreeAndTimeFrameIsThree() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderRSIIndicator rsiIndicator = new WilderRSIIndicator(closePriceIndicator, 3);

		// Act
		Decimal rsi = rsiIndicator.getValue(3);

		// Assert
		this.assertEquals(Decimal.valueOf(40), rsi, Precision);
	}

	@Test
	public void getValueWhenIndexIsTenAndTimeFrameIsThree() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderRSIIndicator rsiIndicator = new WilderRSIIndicator(closePriceIndicator, 3);

		// Act
		Decimal rsi = rsiIndicator.getValue(10);

		// Assert
		this.assertEquals(Decimal.valueOf("58.41730996"), rsi, Precision);
	}
}
