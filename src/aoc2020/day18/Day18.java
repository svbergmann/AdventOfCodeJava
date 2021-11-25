package aoc2020.day18;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.concurrent.atomic.AtomicLong;

public class Day18 extends Day {
	public Day18() {
		super(2020, 18);
	}

	public static long evaluateLeftToRight(@NotNull String input) {
		long res = 0;
		var openParenthesis = 0;
		var closedParenthesis = 0;
		var lastOperator = '+';
		var parenthesisStringBuilder = new StringBuilder();
		for (var i = 0; i < input.length(); i++) {
			var c = input.charAt(i);

			if (c == ' ') continue;

			if (openParenthesis > 0 && c != ')' && openParenthesis != closedParenthesis) {
				if (c == '(') openParenthesis++;
				parenthesisStringBuilder.append(c);
			} else if (Character.isDigit(c)) {
				switch (lastOperator) {
					case '+' -> res += Integer.parseInt(String.valueOf(c));
					case '-' -> res -= Integer.parseInt(String.valueOf(c));
					case '*' -> res *= Integer.parseInt(String.valueOf(c));
					case '/' -> res /= Integer.parseInt(String.valueOf(c));
				}
			} else {
				switch (c) {
					case '+' -> lastOperator = '+';
					case '-' -> lastOperator = '-';
					case '*' -> lastOperator = '*';
					case '/' -> lastOperator = '/';
					case '(' -> openParenthesis++;
					case ')' -> {
						closedParenthesis++;
						if (closedParenthesis == openParenthesis && openParenthesis != 0) {
							switch (lastOperator) {
								case '+' -> res += evaluateLeftToRight(parenthesisStringBuilder.toString());
								case '-' -> res -= evaluateLeftToRight(parenthesisStringBuilder.toString());
								case '*' -> res *= evaluateLeftToRight(parenthesisStringBuilder.toString());
								case '/' -> res /= evaluateLeftToRight(parenthesisStringBuilder.toString());
							}
							openParenthesis = 0;
							closedParenthesis = 0;
							parenthesisStringBuilder.setLength(0);
						} else {
							parenthesisStringBuilder.append(c);
						}
					}
				}
			}
		}
		return res;
	}

	@Override
	public String resultPartOne() {
		var res = new AtomicLong();
		this.input.stream().parallel().forEach(s -> res.addAndGet(evaluateLeftToRight(s)));
		return String.valueOf(res);
	}

	@Override
	public String resultPartTwo() {
		return null;
	}
}
