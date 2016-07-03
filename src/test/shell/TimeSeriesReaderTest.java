package test.shell;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import main.shell.reader.TimeSeriesReader;

public class TimeSeriesReaderTest {

	@Test
	public void readSampleData() throws IOException {
		// Arrange
		String filename = "src/test/resources/SampleData.csv";

		// Act
		TimeSeries timeSeries = TimeSeriesReader.read(filename);

		// Assert
		Tick tick1 = timeSeries.getTick(0);
		Assert.assertEquals(new DateTime(2014, 11, 4, 9, 1), tick1.getEndTime());
		Assert.assertEquals(Decimal.valueOf("2993.65600"), tick1.getOpenPrice());
		Assert.assertEquals(Decimal.valueOf("2993.65600"), tick1.getMaxPrice());
		Assert.assertEquals(Decimal.valueOf("2993.06300"), tick1.getMinPrice());
		Assert.assertEquals(Decimal.valueOf("2993.06300"), tick1.getClosePrice());
		Assert.assertEquals(Decimal.valueOf(19), tick1.getVolume());

		Tick tick2 = timeSeries.getTick(1);
		Assert.assertEquals(new DateTime(2015, 03, 19, 15, 13), tick2.getEndTime());
		Assert.assertEquals(Decimal.valueOf("3811.77700"), tick2.getOpenPrice());
		Assert.assertEquals(Decimal.valueOf("3814.65100"), tick2.getMaxPrice());
		Assert.assertEquals(Decimal.valueOf("3811.77700"), tick2.getMinPrice());
		Assert.assertEquals(Decimal.valueOf("3812.35200"), tick2.getClosePrice());
		Assert.assertEquals(Decimal.valueOf(284), tick2.getVolume());

		Tick tick3 = timeSeries.getTick(2);
		Assert.assertEquals(new DateTime(2016, 06, 22, 17, 59), tick3.getEndTime());
		Assert.assertEquals(Decimal.valueOf("3385.50000"), tick3.getOpenPrice());
		Assert.assertEquals(Decimal.valueOf("3386.00000"), tick3.getMaxPrice());
		Assert.assertEquals(Decimal.valueOf("3384.50000"), tick3.getMinPrice());
		Assert.assertEquals(Decimal.valueOf("3384.50000"), tick3.getClosePrice());
		Assert.assertEquals(Decimal.valueOf(51), tick3.getVolume());

		Tick tick4 = timeSeries.getTick(3);
		Assert.assertEquals(new DateTime(2016, 06, 22, 18, 0), tick4.getEndTime());
		Assert.assertEquals(Decimal.valueOf("3385.00000"), tick4.getOpenPrice());
		Assert.assertEquals(Decimal.valueOf("3388.00000"), tick4.getMaxPrice());
		Assert.assertEquals(Decimal.valueOf("3384.00000"), tick4.getMinPrice());
		Assert.assertEquals(Decimal.valueOf("3387.00000"), tick4.getClosePrice());
		Assert.assertEquals(Decimal.valueOf(202), tick4.getVolume());
	}

}
