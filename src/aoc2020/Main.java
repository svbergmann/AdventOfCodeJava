package aoc2020;

import aoc2020.day1.Day1;
import aoc2020.day10.Day10;
import aoc2020.day11.Day11;
import aoc2020.day12.Day12;
import aoc2020.day13.Day13;
import aoc2020.day14.Day14;
import aoc2020.day15.Day15;
import aoc2020.day16.Day16;
import aoc2020.day17.Day17;
import aoc2020.day2.Day2;
import aoc2020.day3.Day3;
import aoc2020.day4.Day4;
import aoc2020.day5.Day5;
import aoc2020.day6.Day6;
import aoc2020.day7.Day7;
import aoc2020.day8.Day8;
import aoc2020.day9.Day9;
import utils.Day;
import utils.Utilities;

public class Main {

	public static void main(String[] args) {
		printResults(new Day17());
	}

	public static void printResults(Day day) {
		System.out.println("Result of day " + day.number() + ":");
		System.out.println(Utilities.PART_ONE + day.resultPartOne());
		System.out.println(Utilities.PART_TWO + day.resultPartTwo());
	}

	private static Day[] createDayArray() {
		return new Day[]{new Day1(), new Day2(), new Day3(), new Day4(),
				new Day5(), new Day6(), new Day7(), new Day8(), new Day9(),
				new Day10(), new Day11(), new Day12(), new Day13(), new Day14(),
				new Day15(), new Day16(), new Day17()};
	}

}
