package aoc2021.day14;

import org.jetbrains.annotations.NotNull;
import utils.Day;
import utils.Utilities;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
			var line = list.get(i).split("->");
			this.rules.add(new Utilities.Pair<>(line[0].trim(), line[1].trim()));
		}
	}

	@Override
	public String resultPartOne() {
		return this.getResult(false, 10) + "";
	}

	@Override
	public String resultPartTwo() {
		var amounts = this.getAmount(this.getResultingMap(false, 40));
		return amounts.stream().mapToLong(Map.Entry::getValue).max().getAsLong() -
				amounts.stream().mapToLong(Map.Entry::getValue).min().getAsLong() + "";
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

	private @NotNull HashMap<String, Long> getResultingMap(boolean example, int steps) {
		this.init(example);
		var resultingPairsMap = new HashMap<String, Long>();
		for (var i = 0; i < Day14.this.polymerTemplate.length() - 1; i++) {
			var key = "" + Day14.this.polymerTemplate.charAt(i) + Day14.this.polymerTemplate.charAt(i + 1);
			resultingPairsMap.put(key, 0L);
		}
		for (var i = 0; i < steps; i++) {
			var tmpMap = new HashMap<String, Long>();
			for (var pair : this.rules) {
				if (resultingPairsMap.containsKey(pair.getKey())) {
					var valueOfKey = resultingPairsMap.get(pair.getKey()) == 0 ? 1 : resultingPairsMap.get(pair.getKey());
					tmpMap.merge(pair.getKey().charAt(0) + pair.getValue() + "", valueOfKey, Long::sum);
					tmpMap.merge(pair.getValue() + pair.getKey().charAt(1) + "", valueOfKey, Long::sum);
				}
			}
			resultingPairsMap.clear();
			resultingPairsMap.putAll(tmpMap);
		}
		return resultingPairsMap;
	}

	private List<Map.Entry<Character, Long>> getAmount(@NotNull HashMap<String, Long> map) {
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
				amounts.merge(entry.getKey().charAt(0), entry.getValue(), Long::sum);
			}
			if (c == this.polymerTemplate.charAt(this.polymerTemplate.length() - 1)) {
				amounts.merge(c, 1L, Long::sum);
			}
		}
		return amounts.entrySet()
		              .stream()
		              .sorted(Comparator.comparingLong(Map.Entry::getValue))
		              .collect(Collectors.toList());
	}
}
