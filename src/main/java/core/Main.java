package core;

import java.io.IOException;
import java.util.List;

import core.genetic.GeneticAlgorithm;
import core.genetic.GeneticParams;
import core.parameters.RobotParameters;
import eu.verdelhan.ta4j.Decimal;
import shell.global.GlobalSettings;
import shell.io.TimeSeriesIO;

public class Main extends GlobalSettings {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException,
			InstantiationException, ClassNotFoundException, IOException {
		GlobalSettings.timeSeries = TimeSeriesIO.read(args[0]);
		GlobalSettings.numberOfContracts = Decimal.valueOf(1);

		GeneticParams<RobotParameters> geneticParams = new GeneticParams<>(RobotParameters.class);
		List<RobotParameters> strategies = GeneticAlgorithm.run(geneticParams);
		System.out.println(strategies);
	}

}
