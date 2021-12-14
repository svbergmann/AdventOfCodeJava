package aoc2021.day13;

import utils.Day;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends Day {

	private boolean[][] markedPoints;
	private List<Utilities.Pair<String, Integer>> instructionList;

	public Day13() {
		super(2021, 13);
	}

	private void init(boolean example) {
		var list = example ? this.example : this.input;
		var lengthOfMap = list.stream()
		                      .mapToInt(s -> {
			                      if (!s.isEmpty() && !s.startsWith("fold along")) {
				                      return Integer.parseInt(s.split(",")[0]);
			                      }
			                      return 0;
		                      })
		                      .max()
		                      .getAsInt() + 1;
		var heightOfMap = list.stream()
		                      .mapToInt(s -> {
			                      if (!s.isEmpty() && !s.startsWith("fold along")) {
				                      return Integer.parseInt(s.split(",")[1]);
			                      }
			                      return 0;
		                      })
		                      .max()
		                      .getAsInt() + 1;
		this.markedPoints = new boolean[heightOfMap][lengthOfMap];
		this.instructionList = new ArrayList<>();
		for (var s : list) {
			if (!s.isEmpty() && !s.startsWith("fold along")) {
				var s1 = s.split(",");
				this.markedPoints[Integer.parseInt(s1[1])][Integer.parseInt(s1[0])] = true;
			}
			if (s.startsWith("fold along")) {
				var value = Integer.parseInt(s.split("=")[1]);
				if (s.startsWith("fold along y=")) {
					this.instructionList.add(new Utilities.Pair<>("y", value));
				} else if (s.startsWith("fold along x=")) {
					this.instructionList.add(new Utilities.Pair<>("x", value));
				}
			}
		}
	}

	private void executeInstructions(boolean all, int number) {
		var howMany = (all) ? this.instructionList.size() : number;
		for (int i = 0; i < howMany; i++) {
			var instruction = this.instructionList.get(i);
			if (instruction.getKey()
			               .equals("y")) {
				var foldedPaper = new boolean[instruction.getValue()][this.markedPoints[0].length];
				for (var y = 0; y < instruction.getValue(); y++) {
					System.arraycopy(this.markedPoints[y], 0, foldedPaper[y], 0, this.markedPoints[0].length);
				}
				for (var y = this.markedPoints.length - 1; y > instruction.getValue(); y--) {
					for (var x = 0; x < this.markedPoints[0].length; x++) {
						if (this.markedPoints[y][x]) {
							foldedPaper[instruction.getValue() - Math.abs(instruction.getValue() - y)][x] = true;
						}
					}
				}
				this.markedPoints = foldedPaper;
			} else if (instruction.getKey()
			                      .equals("x")) {
				var foldedPaper = new boolean[this.markedPoints.length][instruction.getValue()];
				for (var y = 0; y < this.markedPoints.length; y++) {
					System.arraycopy(this.markedPoints[y], 0, foldedPaper[y], 0, instruction.getValue());
				}
				for (var y = 0; y < this.markedPoints.length; y++) {
					for (var x = this.markedPoints[0].length - 1; x > instruction.getValue(); x--) {
						if (this.markedPoints[y][x]) {
							foldedPaper[y][instruction.getValue() - Math.abs(instruction.getValue() - x)] = true;
						}
					}
				}
				this.markedPoints = foldedPaper;
			}
		}
	}

	@Override
	public String resultPartOne() {
		this.init(false);
		this.executeInstructions(false, 1);
		var res = 0;
		for (var i : this.markedPoints) {
			for (var j : i) {
				if (j) res++;
			}
		}
		return res + "";
	}

	@Override
	public String resultPartTwo() {
		this.init(false);
		this.executeInstructions(true, 1);
		return "\n" + Utilities.getTwoDimArrayString(this.markedPoints);
	}
}
