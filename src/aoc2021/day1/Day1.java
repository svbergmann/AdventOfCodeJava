package aoc2021.day1;

import utils.Day;

public class Day1 extends Day {

	public Day1() {
		super(2021, 1);
	}

	@Override
	public String resultPartOne() {
		var increasedCounter = 0;
		for (var i = 0; i < this.input.size() - 1; i++) {
			if (Integer.parseInt(this.input.get(i)) < Integer.parseInt(this.input.get(i + 1))) {
				increasedCounter++;
			}
		}
		return increasedCounter + "";
	}

	@Override
	public String resultPartTwo() {
		var increasedCounter = 0;
		for (var i = 0; i < this.input.size() - 3; i++) {
			var a1 = Integer.parseInt(this.input.get(i));
			var a2 = Integer.parseInt(this.input.get(i + 1));
			var a3 = Integer.parseInt(this.input.get(i + 2));
			var a4 = Integer.parseInt(this.input.get(i + 3));
			var a = a1 + a2 + a3;
			var b = a2 + a3 + a4;
			if (a < b) increasedCounter++;
		}
		return increasedCounter + "";
	}
}
