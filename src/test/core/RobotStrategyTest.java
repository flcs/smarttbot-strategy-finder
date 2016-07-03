package test.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import main.core.parameter.MovingAverageParameters;
import main.core.parameter.RobotParameters;
import main.core.strategy.RobotStrategy;

public class RobotStrategyTest {

	@Test
	public void backtestSimpleMovingAverage() {
		// Arrange
		int[] closingPrices = { 15, 16, 30, 29, 13, 16, 17, 18, 25, 24, 23, 2, 3, 8 };
		TimeSeries series = TimeSeriesHelper.getTimeSeries(closingPrices);

		MovingAverageParameters movingAverage = MovingAverageParameters.of(3, 6);
		RobotParameters parameters = RobotParameters.of(movingAverage);

		// Act
		List<Trade> trades = RobotStrategy.backtest(series, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(5, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(16), trade1.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getEntry().getAmount());

		Assert.assertEquals(8, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(25), trade1.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade1.getExit().getAmount());

		Trade trade2 = trades.get(1);
		Assert.assertEquals(8, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(25), trade2.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getEntry().getAmount());

		Assert.assertEquals(11, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(2), trade2.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade2.getExit().getAmount());

		Trade trade3 = trades.get(2);
		Assert.assertEquals(11, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(2), trade3.getEntry().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getEntry().getAmount());

		Assert.assertEquals(13, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(8), trade3.getExit().getPrice());
		Assert.assertEquals(Decimal.valueOf(1), trade3.getExit().getAmount());
	}
}
