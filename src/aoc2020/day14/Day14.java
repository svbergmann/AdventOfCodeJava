package aoc2020.day14;

import utils.Day;

import java.util.HashMap;

public class Day14 extends Day {

	private final HashMap<Integer, Long> mem;
	private String mask;

	public Day14() {
		super(2020, 14);
		this.mem = new HashMap<>();
	}

	@Override
	public String resultPartOne() {
		this.input.forEach(s -> {
			if (s.startsWith("mem")) {
				int position = Integer.parseInt(s.split("\\[")[1].split("]")[0]);
				String input = Long.toBinaryString(Long.parseLong(s.split("=")[1].trim()));
				StringBuilder numberAsBinaryStringBuilder = new StringBuilder(input);
				while (numberAsBinaryStringBuilder.length() < 36) {
					numberAsBinaryStringBuilder.insert(0, '0');
				}
				for (int i = 0; i < numberAsBinaryStringBuilder.length(); i++) {
					if (this.mask.charAt(i) != 'X') {
						numberAsBinaryStringBuilder.replace(i, i + 1, String.valueOf(this.mask.charAt(i)));
					}
				}
				this.mem.put(position, Long.parseLong(numberAsBinaryStringBuilder.toString(), 2));
			} else if (s.startsWith("mask")) {
				this.mask = s.split("=")[1].trim();
			}
		});
		long sum = 0;
		for (long value : this.mem.values()) {
			sum += value;
		}
		return sum + "";
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	@Override
	public int number() {
		return 14;
	}
}
