package test.indicators;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import main.core.indicators.AverageType;
import main.core.indicators.WilderAverageIndicator;
import test.AbstractTest;
import test.helpers.PriceSeries;
import test.helpers.PriceType;
import test.helpers.TimeSeriesHelper;

public class WilderAverageGainIndicatorTest extends AbstractTest {
	private static TimeSeries series;

	@BeforeClass
	public static void setupClosingPrices() {
		int[] closingPrices = { 10, 9, 11, 15, 20, 10, 12, 17, 13, 15, 14, 19, 10 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		series = TimeSeriesHelper.getTimeSeries(closeSeries);
	}

	@Test
	public void getValueWhenIndexIsZero() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.GAIN);

		// Act
		Decimal average = averageIndicator.getValue(0);

		// Assert
		Assert.assertEquals(Decimal.ZERO, average);
	}

	@Test
	public void getValueWhenIndexIsZeroAndOtherIndexesWereRetrieved() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.GAIN);

		averageIndicator.getValue(5);
		averageIndicator.getValue(3);
		averageIndicator.getValue(8);

		// Act
		Decimal average = averageIndicator.getValue(0);

		// Assert
		Assert.assertEquals(Decimal.ZERO, average);
	}

	@Test
	public void getValueWhenItIsObtainedBySimpleAverage() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.GAIN);

		// Act
		Decimal average = averageIndicator.getValue(3);

		// Assert
		Assert.assertEquals(Decimal.TWO, average);
	}

	@Test
	public void getValueWhenItIsObtainedBySimpleAverageAndOtherIndexesWereRetrieved() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.GAIN);

		averageIndicator.getValue(5);
		averageIndicator.getValue(3);
		averageIndicator.getValue(8);

		// Act
		Decimal average = averageIndicator.getValue(3);

		// Assert
		Assert.assertEquals(Decimal.TWO, average);
	}

	@Test
	public void getValueWhenItIsObtainedByExponencialAverage() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.GAIN);

		// Act
		Decimal average = averageIndicator.getValue(7);

		// Assert
		Assert.assertEquals(Decimal.THREE, average);
	}

	@Test
	public void getValueWhenItIsObtainedByExponencialAverageAndOtherIndexesWereRetrieved() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.GAIN);

		averageIndicator.getValue(12);
		averageIndicator.getValue(1);
		averageIndicator.getValue(9);

		// Act
		Decimal average = averageIndicator.getValue(7);

		// Assert
		Assert.assertEquals(Decimal.THREE, average);
	}
}
