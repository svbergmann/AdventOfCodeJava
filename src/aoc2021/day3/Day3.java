package aoc2021.day3;

import org.jetbrains.annotations.NotNull;
import utils.Day;

public class Day3 extends Day {

	public Day3() {
		super(2021, 3);
	}

	@Override
	public String resultPartOne() {
		StringBuilder gammaRate = new StringBuilder();
		StringBuilder epsilonRate = new StringBuilder();
		int[] numberOfOneBits = this.getSignificantBits(false);
		for (var i = 0; i < this.input.get(1).length(); i++) {
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

	private int @NotNull [] getSignificantBits(boolean example) {
		var lengthOfArray = example ? this.example.get(1).length() : this.input.get(1).length();
		int[] numberOfOneBits = new int[lengthOfArray];
		for (String s : (example ? this.example : this.input)) {
			for (var j = 0; j < lengthOfArray; j++) {
				if (s.charAt(j) == '1') numberOfOneBits[j]++;
			}
		}
		return numberOfOneBits;
	}
}
