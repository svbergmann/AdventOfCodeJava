package aoc2020.day10;

import utils.Day;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends Day {

	private final List<Integer> sortedJoltAdapters;

	public Day10() {
		super(2020, 10);
		this.sortedJoltAdapters = new ArrayList<>();
		// Add 0 to the beginning
		this.sortedJoltAdapters.add(0);
		this.input.stream()
				.mapToInt(Integer::parseInt)
				.sorted()
				.boxed()
				.forEach(this.sortedJoltAdapters::add);
		// Add the device built in adapter
		this.sortedJoltAdapters.add(this.sortedJoltAdapters.get(this.sortedJoltAdapters.size() - 1) + 3);
	}

	@Override
	public String resultPartOne() {
		int oneJoltDifference = 0;
		int twoJoltDifference = 0;
		int threeJoltDifference = 0;
		for (int i = 1; i < this.sortedJoltAdapters.size(); i++) {
			switch (this.sortedJoltAdapters.get(i) - this.sortedJoltAdapters.get(i - 1)) {
				case 1 -> oneJoltDifference++;
				case 2 -> twoJoltDifference++;
				case 3 -> threeJoltDifference++;
				default -> throw new IllegalArgumentException("There cannot be larger jolt differences than 1, 2 or 3.");
			}
		}
		return String.valueOf(oneJoltDifference * threeJoltDifference);
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	@Override
	public int number() {
		return 10;
	}
}
