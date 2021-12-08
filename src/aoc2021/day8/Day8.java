package aoc2021.day8;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.Day;
import utils.Utilities;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 extends Day {

	private final List<Utilities.Pair<List<String>, List<String>>> setOfDigits;
	private final Map<Integer, Integer> uniqueNumberCount;
	private Map<String, Integer> charsToRenderNumber;

	public Day8() {
		super(2021, 8);

		this.uniqueNumberCount = Map.ofEntries(
				Map.entry(1, 2),
				Map.entry(4, 4),
				Map.entry(7, 3),
				Map.entry(8, 7)
		);

		this.setOfDigits = new ArrayList<>();
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
		return this.setOfDigits.stream().mapToInt(value -> {
			var display = new Display(value.getKey());
			var sb = new StringBuilder();
			for (var digits : value.getValue()) {
				sb.append(display.getDecodedNumber(digits));
			}
			return Integer.parseInt(sb.toString());
		}).sum() + "";
	}

	private static class Display {
		private final char[][] chars;
		String zero;
		String one;
		String two = "";
		String three = "";
		String four;
		String five = "";
		String six = "";
		String seven;
		String eight;
		String nine = "";

		public Display(@NotNull List<String> patterns) {
			this.chars = new char[7][6];
			var sortedPatterns = patterns.stream()
					.map(s -> s.chars()
							.sorted()
							.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
							.toString())
					.sorted(Comparator.comparingInt(String::length))
					.toList();

			this.one = sortedPatterns.get(0);
			this.four = sortedPatterns.get(2);
			this.seven = sortedPatterns.get(1);
			this.eight = sortedPatterns.get(9);
			var zeroOrSixOrNine = sortedPatterns.stream()
					.filter(s -> s.length() == 6)
					.collect(Collectors.toCollection(ArrayList::new));
			var twoOrThreeOrFive = sortedPatterns.stream()
					.filter(s -> s.length() == 5)
					.collect(Collectors.toCollection(ArrayList::new));

			// Compute top
			{
				var sevenSet = new HashSet<Character>();
				for (var c : this.seven.toCharArray()) {
					sevenSet.add(c);
				}
				var oneSet = new HashSet<Character>();
				for (var c : this.one.toCharArray()) {
					oneSet.add(c);
				}
				sevenSet.removeAll(oneSet);

				for (var c : sevenSet) {
					this.chars[0][1] = c;
					this.chars[0][2] = c;
					this.chars[0][3] = c;
					this.chars[0][4] = c;
					break;
				}
			}

			// Compute top right
			{
				for (var number : zeroOrSixOrNine) {
					var numberSet = new HashSet<Character>();
					for (var c : number.toCharArray()) {
						numberSet.add(c);
					}
					var eightSet = new HashSet<Character>();
					for (var c : this.eight.toCharArray()) {
						eightSet.add(c);
					}
					eightSet.removeAll(numberSet);
					if (eightSet.size() == 1) {
						var oneSet = new HashSet<Character>();
						for (var c : this.one.toCharArray()) {
							oneSet.add(c);
						}
						oneSet.retainAll(eightSet);
						if (oneSet.size() == 1) {
							this.six = number;
							for (var c : eightSet) {
								this.chars[1][5] = c;
								this.chars[2][5] = c;
							}
						}
					}
				}
			}

			// Compute bottom right
			{
				var tmp = new StringBuilder(this.seven);
				for (char c : this.seven.toCharArray()) {
					if (this.chars[0][1] == c || this.chars[1][5] == c) {
						tmp.deleteCharAt(tmp.indexOf(String.valueOf(c)));
					}
				}
				this.chars[4][5] = tmp.charAt(0);
				this.chars[5][5] = tmp.charAt(0);
			}

			// Compute bottom
			{
				for (var number : zeroOrSixOrNine) {

					var numberSet = new HashSet<Character>();
					for (var c : number.toCharArray()) {
						numberSet.add(c);
					}
					var sevenSet = new HashSet<Character>();
					for (var c : this.seven.toCharArray()) {
						sevenSet.add(c);
					}
					var fourSet = new HashSet<Character>();
					for (var c : this.four.toCharArray()) {
						fourSet.add(c);
					}

					numberSet.removeAll(sevenSet);
					numberSet.removeAll(fourSet);

					if (numberSet.size() == 1) {
						this.nine = number;
						for (var c : numberSet) {
							this.chars[6][1] = c;
							this.chars[6][2] = c;
							this.chars[6][3] = c;
							this.chars[6][4] = c;
						}
					}
				}
			}

			// Compute bottom left
			{
				var nineSet = new HashSet<Character>();
				for (var c : this.nine.toCharArray()) {
					nineSet.add(c);
				}
				var eightSet = new HashSet<Character>();
				for (var c : this.eight.toCharArray()) {
					eightSet.add(c);
				}
				eightSet.removeAll(nineSet);
				if (eightSet.size() == 1) {
					for (var c : eightSet) {
						this.chars[4][0] = c;
						this.chars[5][0] = c;
					}
				}
			}

			// Compute middle
			{
				for (var number : twoOrThreeOrFive) {
					var numberSet = new HashSet<Character>();
					for (var c : number.toCharArray()) {
						numberSet.add(c);
					}
					numberSet.remove(this.chars[0][1]);
					numberSet.remove(this.chars[1][5]);
					numberSet.remove(this.chars[4][0]);
					numberSet.remove(this.chars[6][1]);
					if (numberSet.size() == 1) {
						for (var c : numberSet) {
							this.chars[3][1] = c;
							this.chars[3][2] = c;
							this.chars[3][3] = c;
							this.chars[3][4] = c;
						}
						this.two = number;
						break;
					}
				}
			}

			// Compute top left
			{
				var completeSet = this.getCompleteSet();
				var eightSet = new HashSet<Character>();
				for (var c : this.eight.toCharArray()) {
					eightSet.add(c);
				}
				eightSet.removeAll(completeSet);
				if (eightSet.size() == 1) {
					for (var c : eightSet) {
						this.chars[1][0] = c;
						this.chars[2][0] = c;
					}
				}
			}

			// Set last numbers
			{
				twoOrThreeOrFive.removeIf(s -> s.equals(this.two));
				for (var number : twoOrThreeOrFive) {
					var completeSet = this.getCompleteSet();
					var numberSet = new HashSet<Character>();
					for (var c : number.toCharArray()) {
						numberSet.add(c);
					}
					completeSet.removeAll(numberSet);
					boolean found = false;
					for (var c : number.toCharArray()) {
						if (c == this.chars[1][0]) {
							found = true;
							break;
						}
					}
					if (found) this.five = number;
					else this.three = number;
				}

				var tmp = new char[]{
						this.chars[0][1],
						this.chars[1][0],
						this.chars[1][5],
						this.chars[4][0],
						this.chars[4][5],
						this.chars[6][1]
				};
				Arrays.sort(tmp);
				this.zero = String.valueOf(tmp);
			}
		}

		public int getDecodedNumber(@NotNull String numberString) {
			var tmp = numberString.toCharArray();
			Arrays.sort(tmp);
			numberString = String.valueOf(tmp);
			if (numberString.equals(this.zero)) return 0;
			if (numberString.equals(this.one)) return 1;
			if (numberString.equals(this.two)) return 2;
			if (numberString.equals(this.three)) return 3;
			if (numberString.equals(this.four)) return 4;
			if (numberString.equals(this.five)) return 5;
			if (numberString.equals(this.six)) return 6;
			if (numberString.equals(this.seven)) return 7;
			if (numberString.equals(this.eight)) return 8;
			if (numberString.equals(this.nine)) return 9;
			throw new IllegalArgumentException("Number not recognized!");
		}

		@Contract(" -> new")
		private @NotNull Set<Character> getCompleteSet() {
			return new HashSet<>() {{
				this.add(Display.this.chars[0][1]);
				this.add(Display.this.chars[1][0]);
				this.add(Display.this.chars[1][5]);
				this.add(Display.this.chars[3][1]);
				this.add(Display.this.chars[4][0]);
				this.add(Display.this.chars[4][5]);
				this.add(Display.this.chars[6][1]);
			}};
		}

		@Override
		public @NotNull String toString() {
			var sb = new StringBuilder();
			for (char[] aChar : this.chars) {
				for (char c : aChar) {
					sb.append(c);
				}
				sb.append("\n");
			}
			return sb.toString();
		}

		public @Nullable String getNumberString(int number) {
			var tmp = "";
			switch (number) {
				case 0 -> tmp = this.zero;
				case 1 -> tmp = this.one;
				case 2 -> tmp = this.two;
				case 3 -> tmp = this.three;
				case 4 -> tmp = this.four;
				case 5 -> tmp = this.five;
				case 6 -> tmp = this.six;
				case 7 -> tmp = this.seven;
				case 8 -> tmp = this.eight;
				case 9 -> tmp = this.nine;
			}
			if (tmp.isEmpty()) return null;
			var sb = new StringBuilder();
			sb.append("Number ").append(number).append(":\n");
			for (char[] aChar : this.chars) {
				for (char c : aChar) {
					boolean found = false;
					for (var c1 : tmp.toCharArray()) {
						if (c == c1) {
							sb.append(c);
							found = true;
							break;
						}
					}
					if (!found) sb.append(" ");
				}
				sb.append("\n");
			}
			return sb.toString();
		}
	}
}
