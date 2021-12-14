package aoc2021.day10;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Day10 extends Day {

	private static final HashMap<Character, Integer> CORRUPTED_CHARACTER_SCORES = new HashMap<>() {{
		this.put(')', 3);
		this.put(']', 57);
		this.put('}', 1197);
		this.put('>', 25137);
	}};

	private static final HashMap<Character, Integer> INCOMPLETE_CHARACTER_SCORES = new HashMap<>() {{
		this.put(')', 1);
		this.put(']', 2);
		this.put('}', 3);
		this.put('>', 4);
	}};

	public Day10() {
		super(2021, 10);
	}

	private static char getInvertedParenthesis(char parenthesis) {
		var c = '0';
		switch (parenthesis) {
			case '(' -> c = ')';
			case ')' -> c = '(';
			case '[' -> c = ']';
			case ']' -> c = '[';
			case '<' -> c = '>';
			case '>' -> c = '<';
			case '{' -> c = '}';
			case '}' -> c = '{';
		}
		return c;
	}

	private static @NotNull HashSet<String> getAllCorruptedLines(@NotNull HashSet<String> input) {
		var res = new HashSet<String>();
		for (var s : input) {
			var stack = new Stack<Character>();
			for (char c : s.toCharArray()) {
				boolean illegalCharacterFound = false;
				switch (c) {
					case '(', '[', '<', '{' -> stack.add(c);
					case ')', ']', '>', '}' -> {
						if (stack.isEmpty()) {
							illegalCharacterFound = true;
						} else if (c == getInvertedParenthesis(stack.peek())) {
							stack.pop();
						} else {
							illegalCharacterFound = true;
						}
					}
				}
				if (illegalCharacterFound) {
					res.add(s);
					break;
				}
			}
		}
		return res;
	}

	private static long getCompletionScore(String s) {
		var res = 0L;
		for (var c : s.toCharArray()) {
			res *= 5;
			res += INCOMPLETE_CHARACTER_SCORES.get(c);
		}
		return res;
	}

	@Override
	public String resultPartOne() {
		var illegalCharacters = new ArrayList<Character>();
		for (var s : this.input) {
			var stack = new Stack<Character>();
			for (char c : s.toCharArray()) {
				boolean illegalCharacterFound = false;
				switch (c) {
					case '(', '[', '<', '{' -> stack.add(c);
					case ')', ']', '>', '}' -> {
						if (stack.isEmpty()) {
							illegalCharacters.add(c);
							illegalCharacterFound = true;
						} else if (c == getInvertedParenthesis(stack.peek())) {
							stack.pop();
						} else {
//							System.out.println(s + " - Expected " + getInvertedParenthesis(stack.peek()) + " but found " + c + " instead");
							illegalCharacters.add(c);
							illegalCharacterFound = true;
						}
					}
				}
				if (illegalCharacterFound) break;
			}
		}
		return illegalCharacters.stream()
		                        .mapToInt(CORRUPTED_CHARACTER_SCORES::get)
		                        .sum() + "";
	}

	@Override
	public String resultPartTwo() {
		var inputSet = new HashSet<>(this.input);
		var corruptedLinesSet = getAllCorruptedLines(inputSet);
		inputSet.removeAll(corruptedLinesSet);
		ArrayList<String> completionStringList = new ArrayList<>();
		for (var s : inputSet) {
			var stack = new Stack<Character>();
			StringBuilder completionString = new StringBuilder();
			for (char c : s.toCharArray()) {
				switch (c) {
					case '(', '[', '<', '{' -> stack.add(c);
					case ')', ']', '>', '}' -> {
						if (c == getInvertedParenthesis(stack.peek())) {
							stack.pop();
						}
					}
				}
			}
			while (!stack.isEmpty()) {
				completionString.append(getInvertedParenthesis(stack.pop()));
			}
			completionStringList.add(completionString.toString());
		}
//		completionStringList.forEach(s -> System.out.println(s + " - " + getCompletionScore(s) + " total points."));
		return (long) completionStringList.stream()
		                                  .mapToLong(Day10::getCompletionScore)
		                                  .sorted()
		                                  .skip((completionStringList.size() - 1) / 2)
		                                  .limit(2 - completionStringList.size() % 2)
		                                  .average()
		                                  .orElse(Double.NaN)
				+ "";
	}
}
