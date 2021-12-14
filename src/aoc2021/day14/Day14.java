package aoc2021.day14;

import org.jetbrains.annotations.NotNull;
import utils.Day;
import utils.Utilities;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day14 extends Day {

	private Set<Utilities.Pair<String, String>> rules;
	private String polymerTemplate;

	public Day14() {
		super(2021, 14);
	}

	private @NotNull HashMap<String, Integer> getResultingMap(boolean example, int steps) {
		this.init(example);
		var resultingPairsMap = new HashMap<String, Integer>();
		for (var i = 0; i < Day14.this.polymerTemplate.length() - 1; i++) {
			var key = "" + Day14.this.polymerTemplate.charAt(i) + Day14.this.polymerTemplate.charAt(i + 1);
			resultingPairsMap.put(key, 0);
		}
		for (var i = 0; i < steps; i++) {
			var tmpMap = new HashMap<String, Integer>();
			for (var pair : this.rules) {
				if (resultingPairsMap.containsKey(pair.getKey())) {
					var firstKey = pair.getKey().charAt(0) + pair.getValue() + "";
					tmpMap.merge(firstKey, 1, (ov, nv) -> ov * 2);
					var secondKey = pair.getValue() + pair.getKey().charAt(1) + "";
					tmpMap.merge(secondKey, 1, (ov, nv) -> ov * 2);
				}
			}
			resultingPairsMap.putAll(tmpMap);
			System.out.println("After step " + i + ":" + resultingPairsMap);
		}
		return resultingPairsMap;
	}

	private void init(boolean example) {
		var list = example ? this.example : this.input;
		this.polymerTemplate = list.get(0);
		this.rules = new HashSet<>();
		for (var i = 2; i < list.size(); i++) {
			var line = list.get(i).split("->");
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
					if (p.getKey().equals(pair)) {
						resultingStringBuilder.append(p.getValue());
						break;
					}
				}
				resultingStringBuilder.append(tmpStringBuilder.charAt(j + 1));
			}
			tmpStringBuilder = resultingStringBuilder;
			resultingStringBuilder = new StringBuilder(resultingStringBuilder.length() * 2);
		}
		return tmpStringBuilder.toString();
	}

	@Override
	public String resultPartOne() {
		return this.getResult(false, 10) + "";
	}

	@Override
	public String resultPartTwo() {
		var map = this.getResultingMap(true, 10);
		var chars = new HashSet<Character>();
		for (var s : map.keySet()) {
			for (var c : s.toCharArray()) {
				chars.add(c);
			}
		}
		var amounts = new HashMap<Character, Long>();
		for (var c : chars) {
			var list = map.entrySet()
			              .stream()
			              .filter(stringIntegerEntry -> stringIntegerEntry.getKey().charAt(0) == c)
			              .toList();
			for (var entry : list) {
				amounts.merge(entry.getKey().charAt(0), Long.valueOf(entry.getValue()), Long::sum);
			}
			if (c == this.polymerTemplate.charAt(this.polymerTemplate.length() - 1)) {
				amounts.merge(c, 1L, Long::sum);
			}
		}
		System.out.println(amounts.entrySet()
		                          .stream()
		                          .sorted(Comparator.comparingLong(Map.Entry::getValue))
		                          .collect(Collectors.toList())
		);
		return amounts.values().stream().max(Long::compareTo).get() - amounts.values().stream().min(Long::compareTo).get() + "";
	}

	private long getResult(boolean example, int steps) {
		this.init(example);
		var res = this.getResultingPolymer(steps);
		var chars = res.chars()
		               .distinct()
		               .sorted()
		               .mapToObj(i -> (char) i)
		               .toList();
		var amounts = chars.stream()
		                   .map(c -> res.chars().filter(c1 -> c1 == c).count())
		                   .collect(Collectors.toCollection(HashSet::new));
		return amounts.stream().max(Long::compareTo).get() - amounts.stream().min(Long::compareTo).get();
	}
}
