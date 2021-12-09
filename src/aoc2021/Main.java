package aoc2021;

import aoc2021.day9.Day9;
import org.jetbrains.annotations.NotNull;
import utils.Day;
import utils.Utilities;

public class Main {

	public static void main(String[] args) {
		printResults(new Day9());
	}

	public static void printResults(@NotNull Day day) {
		System.out.println("Result of day " + day.number + ":");
		System.out.println(Utilities.PART_ONE + day.resultPartOne());
		System.out.println(Utilities.PART_TWO + day.resultPartTwo());
	}
}
