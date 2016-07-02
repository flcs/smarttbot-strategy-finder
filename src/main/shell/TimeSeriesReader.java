package main.shell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;

public class TimeSeriesReader {
	private static final int DATE_TIME = 0;
	private static final int OPEN = 1;
	private static final int HIGH = 2;
	private static final int LOW = 3;
	private static final int CLOSE = 4;
	private static final int VOLUME = 6;

	private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy.MM.dd HH:mm");

	private static final Period DEFAULT_PERIOD = Period.minutes(1);

	public static TimeSeries read(String filename) throws IOException {
		List<Tick> ticks = new ArrayList<>();

		Files.lines(Paths.get(filename)).forEach(line -> {
			String[] tickData = line.split(",");

			DateTime endTime = DateTime.parse(tickData[DATE_TIME], FORMATTER).plus(DEFAULT_PERIOD);
			Decimal openPrice = Decimal.valueOf(tickData[OPEN]);
			Decimal highPrice = Decimal.valueOf(tickData[HIGH]);
			Decimal lowPrice = Decimal.valueOf(tickData[LOW]);
			Decimal closePrice = Decimal.valueOf(tickData[CLOSE]);
			Decimal volume = Decimal.valueOf(tickData[VOLUME]);

			ticks.add(new Tick(DEFAULT_PERIOD, endTime, openPrice, highPrice, lowPrice, closePrice, volume));
		});

		return new TimeSeries(ticks);
	}
}
