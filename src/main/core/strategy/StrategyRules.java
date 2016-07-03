package main.core.strategy;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.trading.rules.CrossedDownIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.CrossedUpIndicatorRule;
import main.core.indicator.WilderRSIIndicator;
import main.core.parameter.RobotParameters;
import main.core.rule.CrossDownRule;
import main.core.rule.CrossUpRule;

public class StrategyRules {

	private final Indicator<Decimal> prices;
	private final RobotParameters parameters;

	private Rule buyEntryRule;
	private Rule buyExitRule;
	private Rule sellEntryRule;
	private Rule sellExitRule;

	public StrategyRules(Indicator<Decimal> prices, RobotParameters parameters) {
		this.prices = prices;
		this.parameters = parameters;
		this.setRules();
	}

	private void setRules() {
		this.setEntryRules();
	}

	private void setEntryRules() {
		this.setMovingAverageRules();
		this.setRSIRules();
	}

	private void setMovingAverageRules() {
		if (parameters.getMovingAverageParameters() == null)
			return;

		int shortPeriods = parameters.getMovingAverageParameters().getShortPeriods();
		SMAIndicator shortSMA = new SMAIndicator(prices, shortPeriods);

		int longPeriods = parameters.getMovingAverageParameters().getLongPeriods();
		SMAIndicator longSMA = new SMAIndicator(prices, longPeriods);

		Rule crossDownRule = new CrossedDownIndicatorRule(shortSMA, longSMA);
		Rule crossUpRule = new CrossedUpIndicatorRule(shortSMA, longSMA);

		buyEntryRule = buyEntryRule == null ? crossUpRule : buyEntryRule.and(crossUpRule);
		buyExitRule = buyExitRule == null ? crossDownRule : buyExitRule.or(crossDownRule);

		sellEntryRule = sellEntryRule == null ? crossDownRule : sellEntryRule.and(crossDownRule);
		sellExitRule = sellExitRule == null ? crossUpRule : sellExitRule.or(crossUpRule);
	}

	private void setRSIRules() {
		if (parameters.getRSIParameters() == null)
			return;

		int periods = parameters.getRSIParameters().getPeriods();
		WilderRSIIndicator rsiIndicator = new WilderRSIIndicator(prices, periods);

		int lower = parameters.getRSIParameters().getLowerValue();
		int upper = parameters.getRSIParameters().getUpperValue();
		Rule crossDownRule = new CrossDownRule(rsiIndicator, Decimal.valueOf(lower), periods);
		Rule crossUpRule = new CrossUpRule(rsiIndicator, Decimal.valueOf(upper), periods);

		buyEntryRule = buyEntryRule == null ? crossDownRule : buyEntryRule.and(crossDownRule);
		buyExitRule = buyExitRule == null ? crossUpRule : buyExitRule.or(crossUpRule);

		sellEntryRule = sellEntryRule == null ? crossUpRule : sellEntryRule.and(crossUpRule);
		sellExitRule = sellExitRule == null ? crossDownRule : sellExitRule.or(crossDownRule);
	}

	public Rule getBuyEntryRule() {
		return buyEntryRule;
	}

	public Rule getBuyExitRule() {
		return buyExitRule;
	}

	public Rule getSellEntryRule() {
		return sellEntryRule;
	}

	public Rule getSellExitRule() {
		return sellExitRule;
	}

}
