package aoc2020.day11;

import utils.Day;

import java.util.ArrayList;

public class Day11 extends Day {

	private char[][] seats;

	public Day11() {
		super(2020, 11);
	}

	@Override
	public String resultPartOne() {
		this.initSeatsArray(false);
		this.modellingSeatsPartOne();
		return String.valueOf(this.countOccupiedSeats());
	}

	@Override
	public String resultPartTwo() {
		this.initSeatsArray(false);
		this.modellingSeatsPartTwo();
		return String.valueOf(this.countOccupiedSeats());
	}

	private void initSeatsArray(boolean example) {
		ArrayList<String> tmp;
		if (example) {
			tmp = this.example;
		} else {
			tmp = this.input;
		}
		this.seats = new char[tmp.size()][tmp.get(0)
		                                     .length()];

		for (int i = 0; i < tmp.size(); i++) {
			this.seats[i] = tmp.get(i)
			                   .toCharArray();
		}
	}

	private void modellingSeatsPartOne() {
		boolean changed = true;
		while (changed) {
			changed = false;
			char[][] tmp = new char[this.seats.length][this.seats[0].length];
			for (int i = 0; i < this.seats.length; i++) {
				for (int j = 0; j < this.seats[i].length; j++) {
					if (this.seats[i][j] == '.') {
						tmp[i][j] = '.';
					} else {
						if (this.seats[i][j] == '#') {
							int occupied = this.checkSurrounding(i, j);
							if (occupied >= 4) {
								tmp[i][j] = 'L';
								changed = true;
							} else {
								tmp[i][j] = '#';
							}
						} else if (this.seats[i][j] == 'L') {
							int occupied = this.checkSurrounding(i, j);
							if (occupied == 0) {
								tmp[i][j] = '#';
								changed = true;
							} else {
								tmp[i][j] = 'L';
							}
						}
					}
				}
			}
			for (int i = 0; i < this.seats.length; i++) {
				System.arraycopy(tmp[i], 0, this.seats[i], 0, this.seats[i].length);
			}
		}
	}

	private void modellingSeatsPartTwo() {
		boolean changed = true;
		while (changed) {
			changed = false;
			char[][] tmp = new char[this.seats.length][this.seats[0].length];
			for (int i = 0; i < this.seats.length; i++) {
				for (int j = 0; j < this.seats[i].length; j++) {
					if (this.seats[i][j] == '.') {
						tmp[i][j] = '.';
					} else {
						if (this.seats[i][j] == '#') {
							int occupied = this.checkVisibleSurrounding(i, j);
							if (occupied >= 5) {
								tmp[i][j] = 'L';
								changed = true;
							} else {
								tmp[i][j] = '#';
							}
						} else if (this.seats[i][j] == 'L') {
							int occupied = this.checkVisibleSurrounding(i, j);
							if (occupied == 0) {
								tmp[i][j] = '#';
								changed = true;
							} else {
								tmp[i][j] = 'L';
							}
						}
					}
				}
			}
			for (int i = 0; i < this.seats.length; i++) {
				System.arraycopy(tmp[i], 0, this.seats[i], 0, this.seats[i].length);
			}
		}
	}

	private void printArray() {
		for (char[] seat : this.seats) {
			for (char c : seat) {
				System.out.print(c);
			}
			System.out.print("\n");
		}
	}

	private int countOccupiedSeats() {
		int occupiedCount = 0;
		for (char[] seat : this.seats) {
			for (char c : seat) {
				if (c == '#') {
					occupiedCount++;
				}
			}
		}
		return occupiedCount;
	}

	private int checkSurrounding(int i, int j) {
		char occupiedChar = '#';
		int occupied = 0;
		// Over
		if (i != 0) {
			if (this.seats[i - 1][j] == occupiedChar) {
				occupied++;
			}
		}
		// Under
		if (i < this.seats.length - 1) {
			if (this.seats[i + 1][j] == occupiedChar) {
				occupied++;
			}
		}
		// Right
		if (j < this.seats[i].length - 1) {
			if (this.seats[i][j + 1] == occupiedChar) {
				occupied++;
			}
		}
		// Left
		if (j != 0) {
			if (this.seats[i][j - 1] == occupiedChar) {
				occupied++;
			}
		}
		// Over left
		if (j != 0 && i != 0) {
			if (this.seats[i - 1][j - 1] == occupiedChar) {
				occupied++;
			}
		}
		// Over right
		if (i != 0 && j < this.seats[i].length - 1) {
			if (this.seats[i - 1][j + 1] == occupiedChar) {
				occupied++;
			}
		}
		// Under left
		if (i < this.seats.length - 1 && j != 0) {
			if (this.seats[i + 1][j - 1] == occupiedChar) {
				occupied++;
			}
		}
		// Under right
		if (i < this.seats.length - 1 && j < this.seats[i].length - 1) {
			if (this.seats[i + 1][j + 1] == occupiedChar) {
				occupied++;
			}
		}
		return occupied;
	}

	private int checkVisibleSurrounding(int i, int j) {
		char occupiedChar = '#';
		char emptySeatChar = 'L';
		int occupied = 0;
		int columns = this.seats[0].length;
		int rows = this.seats.length;
		// Over
		int tmpOver = i;
		while (tmpOver > 0) {
			if (this.seats[tmpOver - 1][j] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[tmpOver - 1][j] == emptySeatChar) {
				break;
			}
			tmpOver--;
		}
		// Under
		int tmpUnder = i;
		while (tmpUnder < rows - 1) {
			if (this.seats[tmpUnder + 1][j] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[tmpUnder + 1][j] == emptySeatChar) {
				break;
			}
			tmpUnder++;
		}
		// Right
		int tmpRight = j;
		while (tmpRight < columns - 1) {
			if (this.seats[i][tmpRight + 1] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[i][tmpRight + 1] == emptySeatChar) {
				break;
			}
			tmpRight++;
		}
		// Left
		int tmpLeft = j;
		while (tmpLeft > 0) {
			if (this.seats[i][tmpLeft - 1] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[i][tmpLeft - 1] == emptySeatChar) {
				break;
			}
			tmpLeft--;
		}
		// Over left
		tmpLeft = j;
		tmpOver = i;
		while (tmpLeft > 0 && tmpOver > 0) {
			if (this.seats[tmpOver - 1][tmpLeft - 1] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[tmpOver - 1][tmpLeft - 1] == emptySeatChar) {
				break;
			}
			tmpLeft--;
			tmpOver--;
		}
		// Over right
		tmpRight = j;
		tmpOver = i;
		while (tmpRight < columns - 1 && tmpOver > 0) {
			if (this.seats[tmpOver - 1][tmpRight + 1] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[tmpOver - 1][tmpRight + 1] == emptySeatChar) {
				break;
			}
			tmpRight++;
			tmpOver--;
		}
		// Under left
		tmpLeft = j;
		tmpUnder = i;
		while (tmpLeft > 0 && tmpUnder < rows - 1) {
			if (this.seats[tmpUnder + 1][tmpLeft - 1] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[tmpUnder + 1][tmpLeft - 1] == emptySeatChar) {
				break;
			}
			tmpLeft--;
			tmpUnder++;
		}
		// Under right
		tmpRight = j;
		tmpUnder = i;
		while (tmpRight < columns - 1 && tmpUnder < rows - 1) {
			if (this.seats[tmpUnder + 1][tmpRight + 1] == occupiedChar) {
				occupied++;
				break;
			}
			if (this.seats[tmpUnder + 1][tmpRight + 1] == emptySeatChar) {
				break;
			}
			tmpRight++;
			tmpUnder++;
		}
		return occupied;
	}
}
