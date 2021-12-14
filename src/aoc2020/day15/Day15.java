package aoc2020.day15;

import utils.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day15 extends Day {
	private List<Integer> spokenNumbers;
	private int lengthOfStartingNumbers;
	private HashMap<Integer, ArrayList<Integer>> integerArrayListHashMap;
	private int lastSpokenNumber;

	public Day15() {
		super(2020, 15);
	}

	private void init() {
		this.spokenNumbers =
				Arrays.stream(this.input.get(0)
				                        .split(","))
				      .mapToInt(Integer::parseInt)
				      .boxed()
				      .collect(Collectors.toList());
		this.lengthOfStartingNumbers = this.spokenNumbers.size();

		this.integerArrayListHashMap = new HashMap<>();
		for (int i = 0; i < this.spokenNumbers.size(); i++) {
			if (!this.integerArrayListHashMap.containsKey(this.spokenNumbers.get(i))) {
				this.integerArrayListHashMap.put(this.spokenNumbers.get(i), new ArrayList<>());
			}
			this.integerArrayListHashMap.get(this.spokenNumbers.get(i))
			                            .add(i);
		}
		this.lastSpokenNumber = this.spokenNumbers.get(this.spokenNumbers.size() - 1);
	}

	@Override
	public String resultPartOne() {
		this.init();
		for (int i = this.lengthOfStartingNumbers; i < 2020; i++) {
			this.playImproved(i);
		}
		return this.lastSpokenNumber + "";
	}

	@Override
	public String resultPartTwo() {
		this.init();
		for (int i = this.lengthOfStartingNumbers; i < 30000000; i++) {
			this.playImproved(i);
		}
		return this.lastSpokenNumber + "";
	}

	private void playImproved(int i) {
		if (this.integerArrayListHashMap.get(this.lastSpokenNumber)
		                                .size() <= 1) {
			if (!this.integerArrayListHashMap.containsKey(0)) {
				this.integerArrayListHashMap.put(0, new ArrayList<>());
			}
			this.integerArrayListHashMap.get(0)
			                            .add(i);
			this.lastSpokenNumber = 0;
		} else {
			ArrayList<Integer> tmp = this.integerArrayListHashMap.get(this.lastSpokenNumber);
			this.lastSpokenNumber = tmp.get(tmp.size() - 1) - tmp.get(tmp.size() - 2);
			if (!this.integerArrayListHashMap.containsKey(this.lastSpokenNumber)) {
				this.integerArrayListHashMap.put(this.lastSpokenNumber, new ArrayList<>());
			}
			this.integerArrayListHashMap.get(this.lastSpokenNumber)
			                            .add(i);
		}
	}

	private void play(int i) {
		int tmp = this.spokenNumbers.get(i - 1);
		if (!this.spokenNumbers.subList(0, i - 1)
		                       .contains(tmp)) {
			this.spokenNumbers.add(0);
		} else {
			int lastIndex = this.spokenNumbers.lastIndexOf(tmp);
			this.spokenNumbers.add(lastIndex - this.spokenNumbers.subList(0, lastIndex)
			                                                     .lastIndexOf(tmp));
		}
	}
}
