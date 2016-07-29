package main.core.rules;

import eu.verdelhan.ta4j.TradingRecord;
import eu.verdelhan.ta4j.trading.rules.AbstractRule;

public class NoExitRule extends AbstractRule {

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return false;
	}

}
