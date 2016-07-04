package main.core.strategy;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.indicators.statistics.StandardDeviationIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.indicators.trackers.bollinger.BollingerBandsLowerIndicator;
import eu.verdelhan.ta4j.indicators.trackers.bollinger.BollingerBandsMiddleIndicator;
import eu.verdelhan.ta4j.indicators.trackers.bollinger.BollingerBandsUpperIndicator;
import eu.verdelhan.ta4j.trading.rules.CrossedDownIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.CrossedUpIndicatorRule;
import main.core.indicator.WilderRSIIndicator;
import main.core.parameter.BollingerBandsParameters;
import main.core.parameter.MovingAverageParameters;
import main.core.parameter.RSIParameters;
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
		this.setBBRules();
	}

	private void setMovingAverageRules() {
		MovingAverageParameters param = parameters.getEntryParameters().getMovingAverageParameters();
		if (param == null)
			return;

		SMAIndicator shortSMA = new SMAIndicator(prices, param.getShortPeriods());
		SMAIndicator longSMA = new SMAIndicator(prices, param.getLongPeriods());

		Rule crossDownRule = new CrossedDownIndicatorRule(shortSMA, longSMA);
		Rule crossUpRule = new CrossedUpIndicatorRule(shortSMA, longSMA);

		buyEntryRule = buyEntryRule == null ? crossDownRule : buyEntryRule.and(crossDownRule);
		buyExitRule = buyExitRule == null ? crossUpRule : buyExitRule.or(crossUpRule);

		sellEntryRule = sellEntryRule == null ? crossUpRule : sellEntryRule.and(crossUpRule);
		sellExitRule = sellExitRule == null ? crossDownRule : sellExitRule.or(crossDownRule);
	}

	private void setRSIRules() {
		RSIParameters param = parameters.getEntryParameters().getRsiParameters();
		if (param == null)
			return;

		WilderRSIIndicator rsiIndicator = new WilderRSIIndicator(prices, param.getPeriods());

		Decimal lowerLimit = Decimal.valueOf(param.getLowerValue());
		Decimal upperLimit = Decimal.valueOf(param.getUpperValue());

		Rule crossDownRule = new CrossDownRule(rsiIndicator, lowerLimit, param.getPeriods());
		Rule crossUpRule = new CrossUpRule(rsiIndicator, upperLimit, param.getPeriods());

		buyEntryRule = buyEntryRule == null ? crossDownRule : buyEntryRule.and(crossDownRule);
		buyExitRule = buyExitRule == null ? crossUpRule : buyExitRule.or(crossUpRule);

		sellEntryRule = sellEntryRule == null ? crossUpRule : sellEntryRule.and(crossUpRule);
		sellExitRule = sellExitRule == null ? crossDownRule : sellExitRule.or(crossDownRule);
	}

	private void setBBRules() {
		BollingerBandsParameters param = parameters.getEntryParameters().getBollingerBandsParameters();
		if (param == null)
			return;

		SMAIndicator simpleMovingAverage = new SMAIndicator(prices, param.getPeriods());
		StandardDeviationIndicator stdDeviation = new StandardDeviationIndicator(prices, param.getPeriods());

		BollingerBandsMiddleIndicator middle = new BollingerBandsMiddleIndicator(simpleMovingAverage);
		BollingerBandsLowerIndicator lower = new BollingerBandsLowerIndicator(middle, stdDeviation, param.getFactor());
		BollingerBandsUpperIndicator upper = new BollingerBandsUpperIndicator(middle, stdDeviation, param.getFactor());

		Rule crossDownRule = new CrossedDownIndicatorRule(prices, lower);
		Rule crossUpRule = new CrossedUpIndicatorRule(prices, upper);

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
