package shell.global;

import eu.verdelhan.ta4j.Decimal;

public class Rand extends shell.util.Rand {

	public static Decimal getDecimal(int lowerValue, int upperValue, int precision) {
		int value = Rand.getInt(lowerValue, upperValue);
		return Decimal.valueOf(value).dividedBy(Decimal.valueOf(precision));
	}

}
