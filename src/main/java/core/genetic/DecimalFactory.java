package core.genetic;

import java.util.HashMap;

import core.gene.GeneFactory;
import eu.verdelhan.ta4j.Decimal;
import shell.global.Rand;

public class DecimalFactory implements GeneFactory<Decimal> {

	@Override
	public Decimal create(HashMap<String, Integer> argsInteger) {
		int min = argsInteger.get("min");
		int max = argsInteger.get("max");
		int precision = argsInteger.get("precision");

		return Rand.getDecimal(min, max, precision);
	}

}
