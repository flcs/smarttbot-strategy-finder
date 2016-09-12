package core.genetic;

import java.util.HashMap;

import core.gene.GeneFactory;
import eu.verdelhan.ta4j.Decimal;
import shell.global.Rand;

public class StopDecimalFactory implements GeneFactory<Decimal> {

	private static final int MIN = 0;
	private static final int MAX = 10000;
	private static final int PRECISION = 100;

	@Override
	public Decimal create(HashMap<String, Integer> argsInteger) {
		return Rand.getDecimal(MIN, MAX, PRECISION);
	}

}
