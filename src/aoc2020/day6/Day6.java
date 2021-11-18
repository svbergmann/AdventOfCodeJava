package aoc2020.day6;

import lombok.Data;
import utils.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Day6 extends Day {

	private final ArrayList<ArrayList<String>> splittedLines;
	private ArrayList<Group> groups;

	public Day6() {
		super(2020, 6);
		this.splittedLines = new ArrayList<>();
		int splittedLinesCounter = 0;
		for (String value : this.input) {
			if (value.isEmpty()) {
				splittedLinesCounter++;
				continue;
			}
			try {
				if (this.splittedLines.get(splittedLinesCounter) == null) {
					this.splittedLines.add(new ArrayList<>());
				}
			} catch (IndexOutOfBoundsException e) {
				this.splittedLines.add(new ArrayList<>());
			}
			for (String s : value.split("\\s")) {
				this.splittedLines.get(splittedLinesCounter).add(s);
			}
		}
	}

	@Override
	public String resultPartOne() {
		this.groups = new ArrayList<>();
		this.splittedLines.forEach(
				s -> {
					Set<Character> questions = new HashSet<>();
					s.forEach(
							s1 -> {
								for (char c : s1.toCharArray()) {
									questions.add(c);
								}
							});
					this.groups.add(new Group(s.size(), questions));
				});
		int res = 0;
		for (Group g : this.groups) {
			res += g.numberOfCorrectAnsweredQuestions();
		}
		return String.valueOf(res);
	}

	@Override
	public String resultPartTwo() {
		this.groups = new ArrayList<>();
		this.splittedLines.forEach(
				s -> {
					Set<Character> correctAnsweredQuestions = new HashSet<>();
					AtomicBoolean firstRun = new AtomicBoolean(true);
					s.forEach(
							s1 -> {
								char[] tmp = s1.toCharArray();
								if (correctAnsweredQuestions.isEmpty() && firstRun.get()) {
									for (char c : tmp) {
										correctAnsweredQuestions.add(c);
									}
								} else {
									ArrayList<Character> tmpList = new ArrayList<>();
									for (char c : tmp) {
										tmpList.add(c);
									}
									correctAnsweredQuestions.removeIf(c -> !tmpList.contains(c));
								}
								firstRun.set(false);
							});
					this.groups.add(new Group(s.size(), correctAnsweredQuestions));
				});
		int counter = 0;
		for (Group g : this.groups) {
			counter += g.numberOfCorrectAnsweredQuestions();
		}
		return String.valueOf(counter);
	}

	@Override
	public int number() {
		return 6;
	}

	@Data
	private static class Group {
		private final int numberOfPeople;
		private final Set<Character> correctAnsweredQuestions;

		Group(int numberOfPeople, Set<Character> correctAnsweredQuestions) {
			this.numberOfPeople = numberOfPeople;
			this.correctAnsweredQuestions = correctAnsweredQuestions;
		}

		int numberOfCorrectAnsweredQuestions() {
			return this.correctAnsweredQuestions.size();
		}
	}
}
