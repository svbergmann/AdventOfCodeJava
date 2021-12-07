package aoc2021.day1;

import utils.Day;

import java.util.List;
import java.util.stream.IntStream;

public class Day1 extends Day {

	private final List<Integer> numbers;

	public Day1() {
		super(2021, 1);
		this.numbers = this.input.stream().mapToInt(Integer::parseInt).boxed().toList();
	}

	@Override
	public String resultPartOne() {
		return IntStream.range(0, this.numbers.size() - 1)
				.filter(i -> this.numbers.get(i) < this.numbers.get(i + 1))
				.count()
				+ "";
	}

	@Override
	public String resultPartTwo() {
		return this.partTwo() + "";
	}

	private int partTwo() {
		var increasedCounter = 0;
		int a1, a2, a3, a4;
		for (var i = 0; i < this.numbers.size() - 3; i++) {
			a1 = this.numbers.get(i);
			a2 = this.numbers.get(i + 1);
			a3 = this.numbers.get(i + 2);
			a4 = this.numbers.get(i + 3);
			if (a1 + a2 + a3 < a2 + a3 + a4) increasedCounter++;
		}
		return increasedCounter;
	}
}
