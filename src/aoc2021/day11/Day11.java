package aoc2021.day11;

import utils.Day;

public class Day11 extends Day {

	private Octupus[][] octopi;

	public Day11() {
		super(2021, 11);
	}

	private void init(boolean example) {
		this.octopi = new Octupus[example ? this.example.size() : this.input.size()]
				[example ? this.example.get(0).toCharArray().length : this.input.get(0).toCharArray().length];
		for (var i = 0; i < 10; i++) {
			var line = example ? this.example.get(i) : this.input.get(i);
			var j = 0;
			for (var c : line.toCharArray()) {
				this.octopi[i][j] = new Octupus(Integer.parseInt(String.valueOf(c)), false);
				j++;
			}
		}
	}

	@Override
	public String resultPartOne() {
		this.init(false);
		var res = 0L;
		for (var i = 0; i < 100; i++) {
			res += this.doStep();
//			var step = i + 1;
//			System.out.println("After step " + step + ":");
//			for (Octupus[] octopus : this.octopi) {
//				for (var j = 0; j < this.octopi[0].length; j++) {
//					System.out.print(octopus[j].value == 0 ? "o" : octopus[j]);
//				}
//				System.out.println();
//			}
		}
		return res + "";
	}

	private long doStep() {
		for (Octupus[] octupuses : this.octopi) {
			for (Octupus octupus : octupuses) {
				octupus.value++;
			}
		}
		for (var i = 0; i < this.octopi.length; i++) {
			for (var j = 0; j < this.octopi[i].length; j++) {
				this.flash(i, j, 0, false);
			}
		}
		var totalFlashes = 0L;
		for (Octupus[] octupuses : this.octopi) {
			for (Octupus octupus : octupuses) {
				if (octupus.flashed) {
					octupus.flashed = false;
					octupus.value = 0;
					totalFlashes++;
				}
			}
		}
		return totalFlashes;
	}

	private void flash(int x, int y, int totalFlashes, boolean flashed) {
		var octupus = this.octopi[x][y];
		if (flashed) octupus.value++;
		if (octupus.flashed) return;
		if (octupus.value > 9) {
			octupus.value = 0;
			octupus.flashed = true;
			totalFlashes++;
			if (x + 1 < this.octopi.length) {
				this.flash(x + 1, y, totalFlashes, true);
				if (y + 1 < this.octopi.length) this.flash(x + 1, y + 1, totalFlashes, true);
			}
			if (x - 1 >= 0) {
				this.flash(x - 1, y, totalFlashes, true);
				if (y - 1 >= 0) this.flash(x - 1, y - 1, totalFlashes, true);
			}
			if (y + 1 < this.octopi.length) {
				this.flash(x, y + 1, totalFlashes, true);
				if (x - 1 >= 0) this.flash(x - 1, y + 1, totalFlashes, true);
			}
			if (y - 1 >= 0) {
				this.flash(x, y - 1, totalFlashes, true);
				if (x + 1 < this.octopi.length) this.flash(x + 1, y - 1, totalFlashes, true);
			}
		}
	}

	@Override
	public String resultPartTwo() {
		this.init(false);
		var amountOfOctopi = this.octopi.length * this.octopi[0].length;
		var step = 0;
		var value = 0L;
		do {
			value = this.doStep();
			step++;
		} while (value != amountOfOctopi);
		return step + "";
	}

	private static class Octupus {
		private int value;
		private boolean flashed;

		public Octupus(int value, boolean flashed) {
			this.value = value;
			this.flashed = flashed;
		}

		@Override
		public String toString() {
			return this.value + "";
		}
	}

}
