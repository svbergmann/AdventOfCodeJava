package aoc2021.day4;

import utils.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 extends Day {

	private List<Integer> drawnNumbers;
	private List<Board> boards;

	public Day4() {
		super(2021, 4);
	}

	@Override
	public String resultPartOne() {
		this.init(false);
		List<Board> winningList;
		for (var i : this.drawnNumbers) {
			this.boards.forEach(board -> board.draw(i));
			winningList = this.boards.stream().parallel().filter(Board::won).toList();
			if (!winningList.isEmpty()) {
				return winningList.get(0).sumUnmarkedNumbers() + " * " + i + " = "
						+ winningList.get(0).sumUnmarkedNumbers() * i + "";
			}
		}
		return "No winning score found!";
	}

	@Override
	public String resultPartTwo() {
		this.init(false);
		for (var i : this.drawnNumbers) {
//			if (this.boards.size() < 4) {
//				this.boards.forEach(board -> {
//					board.printNumbers();
//					System.out.println("---");
//				});
//			}
			this.boards.forEach(board -> board.draw(i));
//			System.out.println(i + " drawn");
//			if (this.boards.size() < 4) {
//				this.boards.forEach(board -> {
//					board.printNumbers();
//					System.out.println("---");
//				});
//			}
			this.boards.removeIf(Board::won);
			if (this.boards.size() == 1) {
				return this.boards.get(0).sumUnmarkedNumbers() + " * " + i + " = "
						+ this.boards.get(0).sumUnmarkedNumbers() * i + "";
			}
		}
		return "No winning score found!";
	}

	private void init(boolean example) {
		this.drawnNumbers = example ?
				new ArrayList<>(
						Arrays.stream(this.example.get(0).split(",")).map(Integer::parseInt).toList()) :
				new ArrayList<>(
						Arrays.stream(this.input.get(0).split(",")).map(Integer::parseInt).toList());

		this.boards = new ArrayList<>();
		var size = example ? this.example.size() : this.input.size();
		for (var i = 2; i < size; i += 6) {
			var tmp = new int[5][5];
			for (var j = 0; j < 5; j++) {
				var split = example ?
						Arrays.stream(this.example.get(i + j).split("\s")).filter(s -> !s.isEmpty()).toList() :
						Arrays.stream(this.input.get(i + j).split("\s")).filter(s -> !s.isEmpty()).toList();
				for (int k = 0; k < 5; k++) {
					tmp[j][k] = Integer.parseInt(split.get(k));
				}
			}
			this.boards.add(new Board(tmp));
		}
	}

	private static class Board {
		private final int[][] numbers;
		private final boolean[][] active;

		public Board(int[][] numbers) {
			this.numbers = new int[numbers.length][numbers.length];
			for (var i = 0; i < numbers.length; i++) {
				System.arraycopy(numbers[i], 0, this.numbers[i], 0, numbers.length);
			}
			this.active = new boolean[numbers.length][numbers.length];
		}

		public void draw(int number) {
			for (var i = 0; i < this.numbers.length; i++) {
				for (var j = 0; j < this.numbers.length; j++) {
					if (this.numbers[i][j] == number) this.active[i][j] = true;
				}
			}
		}

		public boolean won() {
			for (var k = 0; k < this.numbers.length; k++) {
				var winningRow = 0;
				for (var i = 0; i < this.numbers.length; i++) {
					var winningColumn = 0;
					for (var j = 0; j < this.numbers.length; j++) {
						if (this.active[i][j]) {
							winningColumn++;
							if (winningColumn == 5) return true;
							if (k == j) {
								winningRow++;
								if (winningRow == 5) return true;
							}
						}
					}
				}
			}
			return false;
		}

		public int sumUnmarkedNumbers() {
			var res = 0;
			for (var i = 0; i < this.numbers.length; i++) {
				for (var j = 0; j < this.numbers.length; j++) {
					if (!this.active[i][j]) res += this.numbers[i][j];
				}
			}
			return res;
		}

		public void printNumbers() {
			for (var i = 0; i < this.numbers.length; i++) {
				for (var j = 0; j < this.numbers.length; j++) {
					System.out.print((this.active[i][j]) ? String.format("%3d", this.numbers[i][j]) : "  .");
				}
				System.out.println();
			}
		}
	}
}
