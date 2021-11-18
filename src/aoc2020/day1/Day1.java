package aoc2020.day1;

import utils.Day;

import java.util.ArrayList;

public class Day1 extends Day {

	public Day1() {
		super(2020, 1);
	}

	@Override
	public String resultPartOne() {
		ArrayList<Integer> ints = new ArrayList<>();
		this.input.forEach(s -> ints.add(Integer.parseInt(s)));
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < ints.size(); i++) {
			for (int j = i + 1; j < ints.size(); j++) {
				if (ints.get(i) + ints.get(j) == 2020) {
					res.append(ints.get(i))
							.append(" * ")
							.append(ints.get(j))
							.append(" = ")
							.append(ints.get(i) * ints.get(j));
				}
			}
		}
		return res.toString();
	}

	@Override
	public String resultPartTwo() {
		ArrayList<Integer> ints = new ArrayList<>();
		this.input.forEach(s -> ints.add(Integer.parseInt(s)));
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < ints.size(); i++) {
			for (int j = i + 1; j < ints.size(); j++) {
				for (int k = j + 1; k < ints.size(); k++) {
					if (ints.get(i) + ints.get(j) + ints.get(k) == 2020) {
						res.append(ints.get(i))
								.append(" * ")
								.append(ints.get(j))
								.append(" * ")
								.append(ints.get(k))
								.append(" = ")
								.append(ints.get(i) * ints.get(j) * ints.get(k))
								.append("\n");
					}
				}
			}
		}
		return res.toString();
	}

	@Override
	public int number() {
		return 1;
	}
}
