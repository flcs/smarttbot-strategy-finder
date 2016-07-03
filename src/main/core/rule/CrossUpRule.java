package main.core.rule;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.indicators.helpers.CrossIndicator;
import eu.verdelhan.ta4j.indicators.simple.ConstantIndicator;
import eu.verdelhan.ta4j.trading.rules.AbstractRule;

public class CrossUpRule extends AbstractRule {
	private final int periods;
	private CrossIndicator cross;

	public CrossUpRule(Indicator<Decimal> indicator, Decimal threshold) {
		this(indicator, new ConstantIndicator<Decimal>(threshold));
	}

	public CrossUpRule(Indicator<Decimal> indicator, Decimal threshold, int periods) {
		this(indicator, new ConstantIndicator<Decimal>(threshold), periods);
	}

	public CrossUpRule(Indicator<Decimal> first, Indicator<Decimal> second) {
		this(second, first, 0);
	}

	public CrossUpRule(Indicator<Decimal> first, Indicator<Decimal> second, int periods) {
		this.cross = new CrossIndicator(second, first);
		this.periods = periods;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final boolean satisfied = cross.getValue(index);
		traceIsSatisfied(index, satisfied);
		
		if (index < periods)
			return false;
		
		return satisfied;
	}
}
