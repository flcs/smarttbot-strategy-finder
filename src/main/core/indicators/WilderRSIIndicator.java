package main.core.indicators;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.indicators.CachedIndicator;
import main.core.enums.AverageType;

public class WilderRSIIndicator extends CachedIndicator<Decimal> {

	private WilderAverageIndicator averageGainIndicator;
	private WilderAverageIndicator averageLossIndicator;

	public WilderRSIIndicator(Indicator<Decimal> indicator, int timeFrame) {
		super(indicator);
		averageGainIndicator = new WilderAverageIndicator(indicator, timeFrame, AverageType.GAIN);
		averageLossIndicator = new WilderAverageIndicator(indicator, timeFrame, AverageType.LOSS);
	}

	@Override
	protected Decimal calculate(int index) {
		Decimal averageLoss = averageLossIndicator.getValue(index);
		if (averageLoss.isZero()) {
			return Decimal.HUNDRED;
		}

		Decimal averageGain = averageGainIndicator.getValue(index);
		Decimal relativeStrength = averageGain.dividedBy(averageLoss);
		return Decimal.HUNDRED.minus(Decimal.HUNDRED.dividedBy(Decimal.ONE.plus(relativeStrength)));
	}
}
