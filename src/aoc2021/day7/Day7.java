package aoc2021.day7;

import utils.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day7 extends Day {

	private final ArrayList<Integer> crabPositions;

	public Day7() {
		super(2021, 7);
		this.crabPositions = new ArrayList<>(Arrays.stream(this.input.get(0).split(","))
		                                           .map(Integer::parseInt)
		                                           .sorted()
		                                           .toList());
	}

	@Override
	public String resultPartOne() {
		return this.crabPositions.stream()
		                         .map(i -> this.crabPositions
				                         .stream()
				                         .mapToInt(crabPosition -> Math.abs(crabPosition - i))
				                         .sum())
		                         .min(Integer::compareTo)
		                         .get()
				+ "";
	}

	@Override
	public String resultPartTwo() {
		var fuelList = new ArrayList<Integer>();
		for (int j = 0; j < this.crabPositions.get(this.crabPositions.size() - 1); j++) {
			var fuel = 0;
			for (var crabPosition : this.crabPositions) {
				fuel += IntStream.rangeClosed(0, Math.abs(crabPosition - j)).sum();
			}
			fuelList.add(fuel);
		}
		return fuelList.stream()
		               .min(Integer::compareTo)
		               .get() + "";
	}
}
