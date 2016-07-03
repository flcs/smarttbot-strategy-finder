package main.core.parameter;

public class RobotParameters {
	private final MovingAverageParameters movingAverageParameters;
	private final RSIParameters rsiParameters;

	private RobotParameters(MovingAverageParameters movingAverage, RSIParameters rsi) {
		this.movingAverageParameters = movingAverage;
		this.rsiParameters = rsi;
	}

	public static RobotParameters of(MovingAverageParameters movingAverage) {
		return new RobotParameters(movingAverage, null);
	}

	public static RobotParameters of(RSIParameters rsi) {
		return new RobotParameters(null, rsi);
	}

	public MovingAverageParameters getMovingAverageParameters() {
		return movingAverageParameters;
	}

	public RSIParameters getRSIParameters() {
		return rsiParameters;
	}
}
