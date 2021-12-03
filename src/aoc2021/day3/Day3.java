package aoc2021.day3;

import utils.Day;

public class Day3 extends Day {

	public Day3() {
		super(2021, 3);
	}

	@Override
	public String resultPartOne() {
		StringBuilder gammaRate = new StringBuilder();
		StringBuilder epsilonRate = new StringBuilder();
		var lengthOfArray = this.input.get(1).length();
		int[] numberOfOneBits = new int[lengthOfArray];
		for (String s : this.input) {
			for (var j = 0; j < lengthOfArray; j++) {
				if (s.charAt(j) == '1') numberOfOneBits[j]++;
			}
		}
		for (var i = 0; i < lengthOfArray; i++) {
			if (numberOfOneBits[i] > this.input.size() / 2) {
				gammaRate.append("1");
				epsilonRate.append("0");
			} else {
				gammaRate.append("0");
				epsilonRate.append("1");
			}
		}
		return Integer.parseInt(gammaRate.toString(), 2) * Integer.parseInt(epsilonRate.toString(), 2) + "";
	}

	@Override
	public String resultPartTwo() {
		return null;
	}
}
