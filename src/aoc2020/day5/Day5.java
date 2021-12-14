package aoc2020.day5;

import lombok.Data;
import utils.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Day5 extends Day {
	private final ArrayList<Seat> seats;

	public Day5() {
		super(2020, 5);
		this.seats = new ArrayList<>();
		this.input.forEach(s -> {
			char[] chars = s.toCharArray();
			int beginIndex = 0;
			int endIndex = 127;
			int finalRowNumber = 0;
			for (int i = 0; i < 7; i++) {
				switch (chars[i]) {
					case 'F' -> {
						endIndex -= Math.ceil(0.5 * (endIndex - beginIndex));
						if (i == 6) {
							finalRowNumber = endIndex;
						}
					}
					case 'B' -> {
						beginIndex += Math.ceil(0.5 * (endIndex - beginIndex));
						if (i == 6) {
							finalRowNumber = beginIndex;
						}
					}
				}
			}
			int finalColumnNumber = 0;
			beginIndex = 0;
			endIndex = 7;
			for (int i = 7; i <= 9; i++) {
				switch (chars[i]) {
					case 'L' -> {
						endIndex -= Math.ceil(0.5 * (endIndex - beginIndex));
						if (i == 9) {
							finalColumnNumber = endIndex;
						}
					}
					case 'R' -> {
						beginIndex += Math.ceil(0.5 * (endIndex - beginIndex));
						if (i == 9) {
							finalColumnNumber = beginIndex;
						}
					}
				}
			}
			this.seats.add(new Seat(finalRowNumber, finalColumnNumber));
		});

	}

	@Override
	public String resultPartOne() {
		Optional<Seat> s = this.seats.stream()
		                             .max(Comparator.comparingInt(o -> o.seatID));
		return s.map(seat -> String.valueOf(seat.seatID))
		        .orElse(null);
	}

	@Override
	public String resultPartTwo() {
		int seatID = 0;
		ArrayList<Integer> seatNumbers = new ArrayList<>();
		this.seats.forEach(s -> seatNumbers.add(s.seatID));
		seatNumbers.sort(Comparator.comparingInt(o -> o));
		for (int i = 0; i < seatNumbers.size() - 1; i++) {
			int expected = seatNumbers.get(i) + 1;
			int actual = seatNumbers.get(i + 1);
			if (expected != actual) {
				seatID = expected;
			}
		}
		return String.valueOf(seatID);
	}

	@Data
	private static class Seat {
		private final int row;
		private final int column;
		private final int seatID;

		Seat(int row, int column) {
			this.row = row;
			this.column = column;
			this.seatID = this.row * 8 + this.column;
		}
	}
}
