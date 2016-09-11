package indicators;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import core.enums.AverageType;
import core.indicators.WilderAverageIndicator;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import helpers.AbstractTest;
import helpers.PriceSeries;
import helpers.PriceType;
import helpers.TimeSeriesHelper;

public class WilderAverageLossIndicatorTest extends AbstractTest {
	private static TimeSeries series;

	@BeforeClass
	public static void setupClosingPrices() {
		int[] closingPrices = { 20, 15, 14, 18, 16, 11, 15, 7, 6, 15, 14, 19, 10 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closingPrices);
		series = TimeSeriesHelper.getTimeSeries(closeSeries);
	}

	@Test
	public void getValueWhenIndexIsZero() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.LOSS);

		// Act
		Decimal average = averageIndicator.getValue(0);

		// Assert
		Assert.assertEquals(Decimal.ZERO, average);
	}

	@Test
	public void getValueWhenIndexIsZeroAndOtherIndexesWereRetrieved() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.LOSS);

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
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.LOSS);

		// Act
		Decimal average = averageIndicator.getValue(3);

		// Assert
		Assert.assertEquals(Decimal.TWO, average);
	}

	@Test
	public void getValueWhenItIsObtainedBySimpleAverageAndOtherIndexesWereRetrieved() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.LOSS);

		averageIndicator.getValue(5);
		averageIndicator.getValue(3);
		averageIndicator.getValue(8);

		// Act
		Decimal average = averageIndicator.getValue(3);

		// Assert
		Assert.assertEquals(Decimal.TWO, average);
	}

	@Test
	public void getValueWhenItIsObtainedByExponentialAverage() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.LOSS);

		// Act
		Decimal average = averageIndicator.getValue(8);

		// Assert
		Assert.assertEquals(Decimal.THREE, average);
	}

	@Test
	public void getValueWhenItIsObtainedByExponentialAverageAndOtherIndexesWereRetrieved() {
		// Arrange
		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
		WilderAverageIndicator averageIndicator = new WilderAverageIndicator(closePriceIndicator, 3, AverageType.LOSS);

		averageIndicator.getValue(12);
		averageIndicator.getValue(1);
		averageIndicator.getValue(10);

		// Act
		Decimal average = averageIndicator.getValue(8);

		// Assert
		Assert.assertEquals(Decimal.THREE, average);
	}
}
