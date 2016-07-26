package test.strategy;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order.OrderType;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import main.core.enums.ExitType;
import main.core.enums.StopType;
import main.core.parameters.RobotParameters;
import main.core.parameters.entry.BollingerBandsParameters;
import main.core.parameters.entry.EntryParameters;
import main.core.parameters.entry.MovingAverageParameters;
import main.core.parameters.entry.RSIParameters;
import main.core.parameters.exit.ExitParameters;
import main.core.parameters.exit.FixedStopGainParameters;
import main.core.parameters.exit.FixedStopLossParameters;
import main.core.strategy.RobotStrategy;
import test.AbstractTest;
import test.helpers.PriceSeries;
import test.helpers.PriceType;
import test.helpers.TimeSeriesHelper;

public class IndicatorsExitCriteriaTest extends AbstractTest {

	private static TimeSeries timeSeries;
	private static EntryParameters entryParameters;

	@BeforeClass
	public static void setupTimeSeries() {
		int[] closePrices = { 50, 49, 50, 51, 49, 46, 40, 45, 63, 20, 45, 43, 42, 60, 55, 30 };
		PriceSeries closeSeries = new PriceSeries(PriceType.CLOSE, closePrices);
		timeSeries = TimeSeriesHelper.getTimeSeries(closeSeries);

		MovingAverageParameters sma = new MovingAverageParameters(3, 6);
		RSIParameters rsi = new RSIParameters(3, 30, 70);
		entryParameters = new EntryParameters(sma, rsi, null);
	}

	@Test
	public void exitTradeIfAnyIndicatorSignals() {
		// Arrange
		ExitParameters exitParameters = new ExitParameters(ExitType.ANY_INDICATOR, null, null);
		RobotParameters parameters = new RobotParameters(entryParameters, exitParameters);

		// Act
		List<Trade> trades = RobotStrategy.backtest(timeSeries, parameters);

		// Assert
		Trade trade1 = trades.get(0);
		Assert.assertEquals(5, trade1.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade1.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(46), trade1.getEntry().getPrice());

		Assert.assertEquals(8, trade1.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade1.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade1.getExit().getPrice());
		
		Trade trade2 = trades.get(1);
		Assert.assertEquals(8, trade2.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade2.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(63), trade2.getEntry().getPrice());
		
		Assert.assertEquals(9, trade2.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade2.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(20), trade2.getExit().getPrice());
		
		Trade trade3 = trades.get(2);
		Assert.assertEquals(9, trade3.getEntry().getIndex());
		Assert.assertEquals(OrderType.BUY, trade3.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(20), trade3.getEntry().getPrice());
		
		Assert.assertEquals(12, trade3.getExit().getIndex());
		Assert.assertEquals(OrderType.SELL, trade3.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(42), trade3.getExit().getPrice());
		
		Trade trade4 = trades.get(3);
		Assert.assertEquals(13, trade4.getEntry().getIndex());
		Assert.assertEquals(OrderType.SELL, trade4.getEntry().getType());
		Assert.assertEquals(Decimal.valueOf(60), trade4.getEntry().getPrice());
		
		Assert.assertEquals(15, trade4.getExit().getIndex());
		Assert.assertEquals(OrderType.BUY, trade4.getExit().getType());
		Assert.assertEquals(Decimal.valueOf(30), trade4.getExit().getPrice());
	}

	
}