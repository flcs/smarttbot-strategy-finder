package test.core;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class TimeSeriesHelper {
	private static final Period DefaultPeriod = Period.minutes(1);
	private static final DateTime DefaultStartTime = new DateTime(2016, 7, 1, 9, 0);
	private static final Decimal DefaultOpen = Decimal.valueOf(1);
	private static final Decimal DefaultHigh = Decimal.valueOf(1);
	private static final Decimal DefaultLow = Decimal.valueOf(1);
	private static final Decimal DefaultVolume = Decimal.valueOf(1);

	public static TimeSeries getTimeSeries(int[] closingPrices) {
		List<Tick> ticks = new ArrayList<>();
		DateTime currentTime = DefaultStartTime;

		for (int closingPrice : closingPrices) {
			currentTime.plus(DefaultPeriod);
			Decimal close = Decimal.valueOf(closingPrice);
			ticks.add(new Tick(DefaultPeriod, currentTime, DefaultOpen, DefaultHigh, DefaultLow, close, DefaultVolume));
		}

		return new TimeSeries(ticks);
	}

}
