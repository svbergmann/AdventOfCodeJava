package aoc2021.day11;

import utils.Day;

public class Day11 extends Day {

	private final Octupus[][] octopi;

	public Day11() {
		super(2021, 11);
		this.octopi = new Octupus[10][10];
		for (var i = 0; i < 10; i++) {
			var line = this.example.get(i);
			var j = 0;
			for (var c : line.toCharArray()) {
				this.octopi[i][j] = new Octupus(Integer.parseInt(String.valueOf(c)), false);
				j++;
			}
		}
	}

	@Override
	public String resultPartOne() {
		var res = 0L;
		for (var i = 0; i < 10; i++) {
			res += this.doStep();
			var step = i + 1;
			System.out.println("After step " + step + ":");
			for (Octupus[] octopus : this.octopi) {
				for (var j = 0; j < this.octopi[i].length; j++) {
					System.out.print(octopus[j].value == 0 ? "o" : octopus[j]);
				}
				System.out.println();
			}
			System.out.println();
			System.out.println(res);
		}
		return res + "";
	}

	private long doStep() {
		for (Octupus[] octupuses : this.octopi) {
			for (Octupus octupus : octupuses) {
				octupus.value++;
			}
		}
		var totalFlashes = 0L;
		for (var i = 0; i < this.octopi.length; i++) {
			for (var j = 0; j < this.octopi[i].length; j++) {
				totalFlashes += this.flash(i, j, 0, false);
			}
		}
		for (Octupus[] octupuses : this.octopi) {
			for (Octupus octupus : octupuses) {
				octupus.flashed = false;
			}
		}
		return totalFlashes;
	}

	private int flash(int x, int y, int totalFlashes, boolean flashed) {
		var octupus = this.octopi[x][y];
		if (flashed) octupus.value++;
		if (octupus.flashed) return totalFlashes;
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
			return totalFlashes;
		}
		return totalFlashes;
	}

	@Override
	public String resultPartTwo() {
		return null;
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
