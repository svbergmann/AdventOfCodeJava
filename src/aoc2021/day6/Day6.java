package aoc2021.day6;

import utils.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 extends Day {

	public Day6() {
		super(2021, 6);
	}

	@Override
	public String resultPartOne() {
		var list = new ArrayList<>(Arrays.stream(this.input.get(0)
		                                                   .split(","))
		                                 .map(s -> new Lanternfish(Integer.parseInt(s)))
		                                 .toList());
		for (var i = 0; i < 80; i++) {
			list.addAll(list.stream()
			                .filter(Lanternfish::dayPassed)
			                .map(l -> new Lanternfish(8))
			                .collect(Collectors.toCollection(ArrayList::new)));
		}
		return list.size() + "";
	}

	@Override
	public String resultPartTwo() {
		var amount0 = 0L;
		var amount1 = 0L;
		var amount2 = 0L;
		var amount3 = 0L;
		var amount4 = 0L;
		var amount5 = 0L;
		var amount6 = 0L;
		var amount7 = 0L;
		var amount8 = 0L;

		for (var s : this.input.get(0)
		                       .toCharArray()) {
			if (s != ' ') {
				switch (s) {
					case '1' -> amount1++;
					case '2' -> amount2++;
					case '3' -> amount3++;
					case '4' -> amount4++;
					case '5' -> amount5++;
					case '6' -> amount6++;
				}
			}
		}

		for (var i = 0; i < 256; i++) {
			var newFishes = amount0;
			amount0 = amount1;
			amount1 = amount2;
			amount2 = amount3;
			amount3 = amount4;
			amount4 = amount5;
			amount5 = amount6;
			amount6 = amount7 + newFishes;
			amount7 = amount8;
			amount8 = newFishes;
		}

		return amount0 + amount1 + amount2 + amount3 + amount4 + amount5 + amount6 + amount7 + amount8 + "";
	}

	private static class Lanternfish {
		private int internalTimer;

		public Lanternfish(int internalTimer) {
			this.internalTimer = internalTimer;
		}

		/**
		 * Simulates a passed Day.
		 *
		 * @return true if a new fish is created, else false
		 */
		public boolean dayPassed() {
			this.internalTimer--;
			if (this.internalTimer == -1) {
				this.internalTimer = 6;
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return this.internalTimer + "";
		}
	}
}
