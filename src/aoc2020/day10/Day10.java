package aoc2020.day10;

import org.jetbrains.annotations.NotNull;
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
		this.example.stream()
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
		return this.getArrangementsIt(this.sortedJoltAdapters) + "";
	}

	private int getArrangementsRec(@NotNull List<Integer> list) {
		var differentArrangements = 0;
		if (list.size() == 1 || list.size() == 0) return 1;

		differentArrangements += this.getArrangementsRec(list.subList(1, list.size()));

		switch (list.get(1) - list.get(0)) {
			case 1 -> {
				if (list.get(2) - list.get(0) == 2) {
					differentArrangements += this.getArrangementsRec(list.subList(2, list.size()));
					if (list.get(3) - list.get(0) == 3) {
						differentArrangements += this.getArrangementsRec(list.subList(3, list.size()));
					}
				}
				if (list.get(2) - list.get(0) == 3) {
					differentArrangements += this.getArrangementsRec(list.subList(2, list.size()));
				}
			}
			case 2 -> {
				if (list.get(2) - list.get(0) == 3) {
					differentArrangements += this.getArrangementsRec(list.subList(2, list.size()));
				}
			}
		}
		return differentArrangements;
	}

	public long getArrangementsIt(@NotNull List<Integer> list) {
		long differentArrangements = 1;
		var shift = 0;
		var tmp = 0;

		while (list.size() > 1) {
			shift = 0;
			tmp = 1;
			switch (list.get(1) - list.get(0)) {
				case 1 -> {
					switch (list.get(2) - list.get(0)) {
						case 2 -> {
							shift = 2;
							tmp = 2;
							if (list.get(3) - list.get(0) == 3) {
								shift = 3;
								tmp = 4;
							}
						}
						case 3 -> {
							shift = 2;
							tmp = 2;
						}
					}
				}
				case 2 -> {
					if (list.get(2) - list.get(0) == 3) {
						shift = 2;
						tmp = 2;
					}
				}
			}

			differentArrangements *= tmp;
			list = list.subList(1, list.size());
		}
		return differentArrangements;
	}
}
