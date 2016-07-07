package test.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class TimeSeriesHelper {
	private static final Period DefaultPeriod = Period.minutes(1);
	private static final DateTime DefaultStartingTime = new DateTime(2016, 7, 1, 9, 0);
	private static final Decimal DefaultVolume = Decimal.valueOf(1);
	private static Map<PriceType, Decimal> DefaultPrices;

	static {
		DefaultPrices = new HashMap<PriceType, Decimal>();
		DefaultPrices.put(PriceType.OPEN, Decimal.valueOf(1));
		DefaultPrices.put(PriceType.HIGH, Decimal.valueOf(1));
		DefaultPrices.put(PriceType.LOW, Decimal.valueOf(1));
		DefaultPrices.put(PriceType.CLOSE, Decimal.valueOf(1));
	}

	public static TimeSeries getTimeSeries(PriceSeries... priceSeries) {
		return getTimeSeries(DefaultStartingTime, priceSeries);
	}

	public static TimeSeries getTimeSeries(DateTime startingTime, PriceSeries... priceSeries) {
		int numberOfPrices = 0;
		Map<PriceType, List<Decimal>> priceMap = newPriceMap();
		for (PriceSeries prices : priceSeries) {
			priceMap.put(prices.getType(), prices.getPrices());
			numberOfPrices = Math.max(numberOfPrices, prices.getPrices().size());
		}

		List<Tick> ticks = new ArrayList<>();
		DateTime currentTime = startingTime;
		for (int i = 0; i < numberOfPrices; i++) {
			currentTime = currentTime.plus(DefaultPeriod);

			Decimal open = getPrice(priceMap, PriceType.OPEN, i);
			Decimal high = getPrice(priceMap, PriceType.HIGH, i);
			Decimal low = getPrice(priceMap, PriceType.LOW, i);
			Decimal close = getPrice(priceMap, PriceType.CLOSE, i);

			ticks.add(new Tick(DefaultPeriod, currentTime, open, high, low, close, DefaultVolume));
		}

		return new TimeSeries(ticks);
	}

	private static Decimal getPrice(Map<PriceType, List<Decimal>> priceMap, PriceType priceType, int index) {
		List<Decimal> prices = priceMap.get(priceType);
		if (index < prices.size()) {
			return prices.get(index);
		} else {
			return DefaultPrices.get(priceType);
		}
	}

	private static Map<PriceType, List<Decimal>> newPriceMap() {
		Map<PriceType, List<Decimal>> priceMap = new HashMap<>();

		priceMap.put(PriceType.OPEN, new ArrayList<>());
		priceMap.put(PriceType.HIGH, new ArrayList<>());
		priceMap.put(PriceType.LOW, new ArrayList<>());
		priceMap.put(PriceType.CLOSE, new ArrayList<>());

		return priceMap;
	}

}
