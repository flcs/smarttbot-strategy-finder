package main.core.strategy;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.statistics.StandardDeviationIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.indicators.trackers.bollinger.BollingerBandsLowerIndicator;
import eu.verdelhan.ta4j.indicators.trackers.bollinger.BollingerBandsMiddleIndicator;
import eu.verdelhan.ta4j.indicators.trackers.bollinger.BollingerBandsUpperIndicator;
import eu.verdelhan.ta4j.trading.rules.OverIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.StopGainRule;
import eu.verdelhan.ta4j.trading.rules.StopLossRule;
import eu.verdelhan.ta4j.trading.rules.UnderIndicatorRule;
import main.core.indicator.WilderRSIIndicator;
import main.core.parameter.BollingerBandsParameters;
import main.core.parameter.ExitParameters;
import main.core.parameter.MovingAverageParameters;
import main.core.parameter.RSIParameters;
import main.core.parameter.RobotParameters;

public class StrategyRules {
	private final ClosePriceIndicator prices;
	private final RobotParameters parameters;

	private Rule buyEntryRule;
	private Rule buyExitRule;
	private Rule sellEntryRule;
	private Rule sellExitRule;

	public StrategyRules(ClosePriceIndicator prices, RobotParameters parameters) {
		this.prices = prices;
		this.parameters = parameters;

		this.setRules();
	}

	private void setRules() {
		this.setEntryRules();
		this.setExitRules();
	}

	private void setEntryRules() {
		this.setMovingAverageRules();
		this.setRSIRules();
		this.setBBRules();
	}

	private void setExitRules() {
		this.setFixedStopLoss();
	}

	private void setMovingAverageRules() {
		MovingAverageParameters param = parameters.getEntryParameters().getMovingAverageParameters();
		if (param == null)
			return;

		SMAIndicator shortSMA = new SMAIndicator(prices, param.getShortPeriods());
		SMAIndicator longSMA = new SMAIndicator(prices, param.getLongPeriods());

		Rule underRule = new UnderIndicatorRule(shortSMA, longSMA);
		Rule overRule = new OverIndicatorRule(shortSMA, longSMA);

		buyEntryRule = buyEntryRule == null ? underRule : buyEntryRule.and(underRule);
		buyExitRule = buyExitRule == null ? overRule : buyExitRule.or(overRule);

		sellEntryRule = sellEntryRule == null ? overRule : sellEntryRule.and(overRule);
		sellExitRule = sellExitRule == null ? underRule : sellExitRule.or(underRule);
	}

	private void setRSIRules() {
		RSIParameters param = parameters.getEntryParameters().getRsiParameters();
		if (param == null)
			return;

		WilderRSIIndicator rsiIndicator = new WilderRSIIndicator(prices, param.getPeriods());

		Decimal lowerLimit = Decimal.valueOf(param.getLowerValue());
		Decimal upperLimit = Decimal.valueOf(param.getUpperValue());

		Rule underRule = new UnderIndicatorRule(rsiIndicator, lowerLimit);
		Rule overRule = new OverIndicatorRule(rsiIndicator, upperLimit);

		buyEntryRule = buyEntryRule == null ? underRule : buyEntryRule.and(underRule);
		buyExitRule = buyExitRule == null ? overRule : buyExitRule.or(overRule);

		sellEntryRule = sellEntryRule == null ? overRule : sellEntryRule.and(overRule);
		sellExitRule = sellExitRule == null ? underRule : sellExitRule.or(underRule);
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

		Rule underRule = new UnderIndicatorRule(prices, lower);
		Rule overRule = new OverIndicatorRule(prices, upper);

		buyEntryRule = buyEntryRule == null ? underRule : buyEntryRule.and(underRule);
		buyExitRule = buyExitRule == null ? overRule : buyExitRule.or(overRule);

		sellEntryRule = sellEntryRule == null ? overRule : sellEntryRule.and(overRule);
		sellExitRule = sellExitRule == null ? underRule : sellExitRule.or(underRule);
	}

	private void setFixedStopLoss() {
		ExitParameters exitParam = parameters.getExitParameters();
		if (exitParam == null)
			return;

		Decimal fixedStopLoss = exitParam.getStopLoss();
		if (fixedStopLoss == null)
			return;

		// Ta4j framework has a limitation and doesn't consider if it is a buying or a selling trade.
		// Because of this, we need to use stop gain as a stop loss in a selling trade.
		StopLossRule stopLossOnBuying = new StopLossRule(prices, fixedStopLoss);
		StopGainRule stopLossOnSelling = new StopGainRule(prices, fixedStopLoss);

		buyExitRule = buyExitRule == null ? stopLossOnBuying : buyExitRule.or(stopLossOnBuying);
		sellExitRule = sellExitRule == null ? stopLossOnSelling : sellExitRule.or(stopLossOnSelling);
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
