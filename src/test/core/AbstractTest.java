package test.core;

import org.junit.Assert;

import eu.verdelhan.ta4j.Decimal;

public abstract class AbstractTest {

	public static final Decimal Precision = Decimal.valueOf("0.000001");

	protected void assertEquals(Decimal expected, Decimal actual, Decimal delta) {
		if (!expected.minus(actual).abs().isLessThanOrEqual(delta)) {
			Assert.fail("expected: <" + expected + "> but was: <" + actual + ">");
		}
	}
}
