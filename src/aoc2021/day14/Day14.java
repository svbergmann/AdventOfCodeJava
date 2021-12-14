package aoc2021.day14;

import org.jetbrains.annotations.NotNull;
import utils.Day;
import utils.Utilities;

import java.util.HashSet;
import java.util.Set;

public class Day14 extends Day {

	private Set<Utilities.Pair<String, String>> rules;
	private String polymerTemplate;

	public Day14() {
		super(2021, 14);
	}

	private void init(boolean example) {
		var list = example ? this.example : this.input;
		this.polymerTemplate = list.get(0);
		this.rules = new HashSet<>();
		for (var i = 2; i < list.size(); i++) {
			var line = list.get(i)
			               .split("->");
			this.rules.add(new Utilities.Pair<>(line[0].trim(), line[1].trim()));
		}
	}

	private @NotNull String getResultingPolymer(int steps) {
		var resultingStringBuilder = new StringBuilder();
		var tmpStringBuilder = new StringBuilder(this.polymerTemplate);
		for (var i = 0; i < steps; i++) {
			resultingStringBuilder.append(tmpStringBuilder.charAt(0));
			for (var j = 0; j < tmpStringBuilder.length() - 1; j++) {
				var pair = "" + tmpStringBuilder.charAt(j) + tmpStringBuilder.charAt(j + 1);
				for (var p : this.rules) {
					if (p.getKey()
					     .equals(pair)) {
						resultingStringBuilder.append(p.getValue());
						break;
					}
				}
				resultingStringBuilder.append(tmpStringBuilder.charAt(j + 1));
			}
			tmpStringBuilder = resultingStringBuilder;
			resultingStringBuilder = new StringBuilder(resultingStringBuilder.length() * 2);
			System.out.println(i);
		}
		return tmpStringBuilder.toString();
	}

	@Override
	public String resultPartOne() {
		return this.getResult(false, 10) + "";
	}

	@Override
	public String resultPartTwo() {
		return this.getResult(true, 40) + "";
	}

	private long getResult(boolean example, int steps) {
		this.init(example);
		var res = this.getResultingPolymer(steps);
		var chars = res.chars()
		               .distinct()
		               .sorted()
		               .mapToObj(i -> (char) i)
		               .toList();
		var amounts = new HashSet<Long>();
		for (var c : chars) {
			amounts.add(res.chars().filter(c1 -> c1 == c).count());
		}
		return amounts.stream().max(Long::compareTo).get() - amounts.stream().min(Long::compareTo).get();
	}
}
