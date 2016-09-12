package shell.global;

import java.time.LocalTime;

import eu.verdelhan.ta4j.Decimal;

public class Rand extends shell.util.Rand {

	public static Decimal getDecimal(int lowerValue, int upperValue, int precision) {
		int value = Rand.getInt(lowerValue, upperValue);
		return Decimal.valueOf(value).dividedBy(Decimal.valueOf(precision));
	}

	public static LocalTime getLocalTime(int minHour, int maxHour, int minMinute, int maxMinute) {
		return LocalTime.of(Rand.getInt(minHour, maxHour), Rand.getInt(minMinute, maxMinute));
	}

}
