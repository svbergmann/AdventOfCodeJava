package utils;

import java.util.List;

public abstract class Day {

	public final int number;
	public final int year;
	protected final List<String> example;
	protected final List<String> input;

	public Day(int year, int numberOfDay) {
		this.number = numberOfDay;
		this.year = year;
		this.example = Utilities.readFromFile(year, numberOfDay, true);
		this.input = Utilities.readFromFile(year, numberOfDay, false);
	}

	public abstract String resultPartOne();

	public abstract String resultPartTwo();

}
