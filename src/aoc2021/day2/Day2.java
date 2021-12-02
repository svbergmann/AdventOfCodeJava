package aoc2021.day2;

import utils.Day;

public class Day2 extends Day {

	public Day2() {
		super(2021, 2);
	}

	@Override
	public String resultPartOne() {
		var horizontal = 0;
		var vertical = 0;
		for (var s : this.input) {
			var value = Integer.parseInt(s.substring(s.length() - 1));
			if (s.startsWith("forward")) horizontal += value;
			else if (s.startsWith("down")) vertical += value;
			else if (s.startsWith("up")) vertical -= value;
		}
		return horizontal * vertical + "";
	}

	@Override
	public String resultPartTwo() {
		var horizontal = 0;
		var depth = 0;
		var aim = 0;
		for (var s : this.input) {
			var value = Integer.parseInt(s.substring(s.length() - 1));
			if (s.startsWith("forward")) {
				horizontal += value;
				depth += aim * value;
			} else if (s.startsWith("down")) {
				aim += value;
			} else if (s.startsWith("up")) {
				aim -= value;
			}
		}
		return horizontal * depth + "";
	}
}
