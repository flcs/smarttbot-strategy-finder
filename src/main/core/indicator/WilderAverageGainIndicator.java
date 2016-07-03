package main.core.indicator;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.AbstractIndicator;

public class WilderAverageGainIndicator extends AbstractIndicator<Decimal> {
	private final Indicator<Decimal> indicator;
	private final int timeFrame;

	private int currentIndex = 0;

	private Decimal sma[];
	private Decimal ema;
	private Decimal lastEma;

	public WilderAverageGainIndicator(Indicator<Decimal> indicator, int timeFrame) {
		super(indicator.getTimeSeries());
		this.indicator = indicator;
		this.timeFrame = timeFrame;
		this.sma = new Decimal[timeFrame];

		TimeSeries series = indicator.getTimeSeries();
		Decimal sum = Decimal.valueOf(0);
		for (int i = 0; i < timeFrame && i < series.getEnd(); i++) {
			sum = sum.plus(getGain(i + 1));
			sma[i] = sum.dividedBy(Decimal.valueOf(i + 1));
		}

		this.ema = sma[timeFrame - 1];
	}

	@Override
	public Decimal getValue(int index) {
		if (index == 0)
			return null;

		if (index <= timeFrame) {
			return sma[index - 1];
		}

		if (index < currentIndex - 1) {
			throw new IllegalStateException("Index: " + index + " - current index: " + currentIndex);
		}

		if (currentIndex == index) {
			return ema;
		}

		if (index < currentIndex) {
			return lastEma;
		}

		currentIndex = index;
		lastEma = ema;
		ema = ema.multipliedBy(Decimal.valueOf(timeFrame - 1)).plus(getGain(index))
				.dividedBy(Decimal.valueOf(timeFrame));
		return ema;
	}

	public Decimal getGain(int index) {
		Decimal change = indicator.getValue(index).minus(indicator.getValue(index - 1));
		return change.max(Decimal.valueOf(0));
	}
}
