package core.genetic;

import java.time.LocalTime;
import java.util.HashMap;

import core.gene.GeneFactory;
import shell.global.Rand;

public class LocalTimeFactory implements GeneFactory<LocalTime> {

	private static final int HOUR_MIN = 9;
	private static final int HOUR_MAX = 17;
	private static final int MINUTE_MIN = 0;
	private static final int MINUTE_MAX = 59;

	@Override
	public LocalTime create(HashMap<String, Integer> argsInteger) {
		return Rand.getLocalTime(HOUR_MIN, HOUR_MAX, MINUTE_MIN, MINUTE_MAX);
	}

}
