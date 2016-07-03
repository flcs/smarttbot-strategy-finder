package main.core.parameter;

public class RobotParameters {
	private final MovingAverageParameters movingAverageParameters;

	private RobotParameters(MovingAverageParameters movingAverage) {
		this.movingAverageParameters = movingAverage;
	}

	public static RobotParameters of(MovingAverageParameters movingAverage) {
		return new RobotParameters(movingAverage);
	}

	public MovingAverageParameters getMovingAverageParameters() {
		return movingAverageParameters;
	}
}
