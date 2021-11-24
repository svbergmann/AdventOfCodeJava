package aoc2020.day18;

import org.jetbrains.annotations.NotNull;
import utils.Day;

public class Day18 extends Day {
	public Day18() {
		super(2020, 18);
	}

	@Override
	public String resultPartOne() {
		return String.valueOf(this.evaluateLeftToRight(this.example.get(0)));
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	@Override
	public int number() {
		return 18;
	}

	public int evaluateLeftToRight(@NotNull String input) {
		var res = 0;
		var paranthesisOpen = false;
		var lastOperator = '+';
		var paranthesisStringBuilder = new StringBuilder();
		for (var i = 0; i < input.length(); i++) {
			var c = input.charAt(i);
			if (c != ' ') {
				if (Character.isDigit(c)) {
					if (paranthesisOpen) {
						paranthesisStringBuilder.append(c);
					}
					res = Integer.parseInt(String.valueOf(c));
				} else {
					switch (c) {
						case '+' -> lastOperator = '+';
						case '-' -> lastOperator = '-';
						case '*' -> lastOperator = '*';
						case '/' -> lastOperator = '/';
						case '(' -> paranthesisOpen = true;
						case ')' -> {
							switch (lastOperator) {
								case '+' -> res += this.evaluateLeftToRight(paranthesisStringBuilder.toString());
								case '-' -> res -= this.evaluateLeftToRight(paranthesisStringBuilder.toString());
								case '*' -> res *= this.evaluateLeftToRight(paranthesisStringBuilder.toString());
								case '/' -> res /= this.evaluateLeftToRight(paranthesisStringBuilder.toString());
							}
							paranthesisOpen = false;
							paranthesisStringBuilder.setLength(0);
						}
					}
				}
			}
		}
		return res;
	}
}
