package main.core.indicator;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.indicators.AbstractIndicator;

public class WilderRSIIndicator extends AbstractIndicator<Decimal> {
	private final int timeFrame;

	private WilderAverageGainIndicator averageGainIndicator;
	private WilderAverageLossIndicator averageLossIndicator;

	public WilderRSIIndicator(Indicator<Decimal> indicator, int timeFrame) {
		super(indicator.getTimeSeries());
		this.timeFrame = timeFrame;
		averageGainIndicator = new WilderAverageGainIndicator(indicator, timeFrame);
		averageLossIndicator = new WilderAverageLossIndicator(indicator, timeFrame);
	}

	@Override
	public String toString() {
		return getClass().getName() + " timeFrame: " + timeFrame;
	}

	private Decimal relativeStrength(int index) {
		if (index == 0) {
			return Decimal.ZERO;
		}
		Decimal averageGain = averageGainIndicator.getValue(index);
		Decimal averageLoss = averageLossIndicator.getValue(index);

		return averageLoss.equals(Decimal.valueOf(0)) ? Decimal.valueOf(100) : averageGain.dividedBy(averageLoss);
	}

	@Override
	public Decimal getValue(int index) {
		return Decimal.HUNDRED.minus(Decimal.HUNDRED.dividedBy(Decimal.ONE.plus(relativeStrength(index))));
	}
}
