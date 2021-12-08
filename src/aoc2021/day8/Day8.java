package aoc2021.day8;

import utils.Day;
import utils.Utilities;

import java.util.*;

public class Day8 extends Day {

	private final Set<Utilities.Pair<List<String>, List<String>>> setOfDigits;
	private final Map<Integer, Character[]> charsToRenderNumber;
	private final Map<Integer, Integer> uniqueNumberCount;

	public Day8() {
		super(2021, 8);
		this.charsToRenderNumber = Map.ofEntries(
				Map.entry(0, new Character[]{'a', 'b', 'c', 'e', 'f', 'g'}),
				Map.entry(1, new Character[]{'c', 'f'}),
				Map.entry(2, new Character[]{'a', 'c', 'd', 'e', 'g'}),
				Map.entry(3, new Character[]{'a', 'c', 'd', 'f', 'g'}),
				Map.entry(4, new Character[]{'b', 'c', 'd', 'f'}),
				Map.entry(5, new Character[]{'a', 'b', 'd', 'f', 'g'}),
				Map.entry(6, new Character[]{'a', 'b', 'd', 'e', 'f', 'g'}),
				Map.entry(7, new Character[]{'a', 'c', 'f'}),
				Map.entry(8, new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'}),
				Map.entry(9, new Character[]{'a', 'b', 'c', 'd', 'f', 'g'})
		);

		this.uniqueNumberCount = Map.ofEntries(
				Map.entry(1, 2),
				Map.entry(4, 4),
				Map.entry(7, 3),
				Map.entry(8, 7)
		);

		this.setOfDigits = new HashSet<>();
		for (String s : this.input) {
			var split = s.split("\\|");
			this.setOfDigits.add(new Utilities.Pair<>(Arrays.stream(split[0].trim().split("\s")).toList(),
					Arrays.stream(split[1].trim().split("\s")).toList()));
		}
	}

	@Override
	public String resultPartOne() {
		var output = new ArrayList<Utilities.Pair<Integer, String>>();
		for (var line : this.setOfDigits) {
			for (var digits : line.getValue()) {
				for (var entry : this.uniqueNumberCount.entrySet()) {
					if (digits.length() == entry.getValue()) {
						output.add(new Utilities.Pair<>(entry.getValue(), digits));
					}
				}
			}
		}
		return output.size() + "";
	}

	@Override
	public String resultPartTwo() {
		return "";
	}
}
