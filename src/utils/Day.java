package utils;

import java.util.ArrayList;

public abstract class Day {

	public final int number;
	protected final ArrayList<String> example;
	protected final ArrayList<String> input;

	public Day(int year, int numberOfDay) {
		this.number = numberOfDay;
		this.example = Utilities.readFromFile(year, numberOfDay, true);
		this.input = Utilities.readFromFile(year, numberOfDay, false);
	}

	public abstract String resultPartOne();

	public abstract String resultPartTwo();
}
