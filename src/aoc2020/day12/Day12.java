package aoc2020.day12;

import utils.Day;

public class Day12 extends Day {
	public Day12() {
		super(2020, 12);
	}

	@Override
	public String resultPartOne() {
		Ship ship = new Ship();
		this.input.forEach(s -> {
			Instruction instruction = new Instruction(s.charAt(0), Integer.parseInt(s.substring(1)));
			ship.move(instruction);
		});
		return ship.positionEast + ship.positionSouth + "";
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	private static class Ship {
		char movingDirection = 'E';
		int positionEast = 0;
		int positionSouth = 0;

		void move(Instruction instruction) {
			switch (instruction.direction) {
				case 'F' -> this.moveHelp(this.movingDirection, instruction.value);

				// falls through
				case 'R', 'L' -> this.movingDirection =
						this.getNextDirectionWithDegrees(
								this.movingDirection, instruction.direction, instruction.value);
				case 'N', 'S', 'E', 'W' -> this.moveHelp(instruction.direction, instruction.value);
				default -> throw new IllegalArgumentException("Direction not supported.");
			}
		}

		private void moveHelp(char direction, int value) {
			switch (direction) {
				case 'E' -> this.positionEast += value;
				case 'W' -> this.positionEast -= value;
				case 'S' -> this.positionSouth += value;
				case 'N' -> this.positionSouth -= value;
			}

		}

		private char getNextDirectionWithDegrees(char direction, char turn, int numberOfDegrees) {
			switch (numberOfDegrees) {
				case 90:
					if (turn == 'R') {
						return this.getNextDirection(direction);
					} else if (turn == 'L') {
						return this.getPreviousDirection(direction);
					}
				case 180:
					if (turn == 'R') {
						return this.getNextDirection(this.getNextDirection(direction));
					} else if (turn == 'L') {
						return this.getPreviousDirection(this.getPreviousDirection(direction));
					}
				case 270:
					if (turn == 'R') {
						return this.getNextDirection(
								this.getNextDirection(
										this.getNextDirection(direction)));
					} else if (turn == 'L') {
						return this.getPreviousDirection(
								this.getPreviousDirection(
										this.getPreviousDirection(direction)));
					}
				default:
					return direction;
			}
		}

		private char getNextDirection(char direction) {
			return switch (direction) {
				case 'E' -> 'S';
				case 'W' -> 'N';
				case 'S' -> 'W';
				case 'N' -> 'E';
				default -> throw new IllegalArgumentException("Direction not supported.");
			};
		}

		private char getPreviousDirection(char direction) {
			return switch (direction) {
				case 'E' -> 'N';
				case 'W' -> 'S';
				case 'S' -> 'E';
				case 'N' -> 'W';
				default -> throw new IllegalArgumentException("Direction not supported.");
			};
		}

	}

	private record Instruction(char direction, int value) {
	}
}
