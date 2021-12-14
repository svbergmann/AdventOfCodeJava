package aoc2020.day16;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day16 extends Day {

	private final List<String> nearbyTickets;
	private final Rules rules;

	public Day16() {
		super(2020, 16);
		this.rules = new Rules();
		for (int i = 0; ; i++) {
			if (this.input.get(i)
			              .isEmpty()) {
				break;
			} else {
				String[] splittedRule = this.input.get(i)
				                                  .split(":");
				int[] ruleNumbers = new int[4];
				ruleNumbers[0] = Integer.parseInt(splittedRule[1].trim()
				                                                 .split("-")[0]);
				ruleNumbers[1] =
						Integer.parseInt(splittedRule[1].trim()
						                                .split("-")[1].split("or")[0].trim());
				ruleNumbers[2] =
						Integer.parseInt(splittedRule[1].trim()
						                                .split("or")[1].trim()
						                                               .split("-")[0].trim());
				ruleNumbers[3] =
						Integer.parseInt(splittedRule[1].trim()
						                                .split("or")[1].trim()
						                                               .split("-")[1].trim());
				this.rules.addRule(new AbstractMap.SimpleEntry<>(splittedRule[0], ruleNumbers));
			}
		}
		this.nearbyTickets =
				this.input.subList(this.input.indexOf("nearby tickets:") + 1, this.input.size());
	}

	@Override
	public String resultPartOne() {
		AtomicInteger counter = new AtomicInteger();
		this.nearbyTickets.stream()
		                  .parallel()
		                  .forEach(
				                  s ->
						                  Arrays.stream(s.split(","))
						                        .parallel()
						                        .forEach(
								                        s1 -> {
									                        if (!this.rules.isValidNumber(Integer.parseInt(s1))) {
										                        counter.addAndGet(Integer.parseInt(s1));
									                        }
								                        }));
		return counter.toString();
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	private static class Rules {
		private final HashMap<String, int[]> hashMap;

		@SafeVarargs
		Rules(Map.Entry<String, int[]> @NotNull ... rule) {
			this.hashMap = new HashMap<>();
			for (Map.Entry<String, int[]> pair : rule) {
				this.addRule(pair);
			}
		}

		void addRule(Map.Entry<String, int[]> rule) {
			this.hashMap.put(rule.getKey(), rule.getValue());
		}

		boolean isValidNumber(int i) {
			for (Map.Entry<String, int[]> entry : this.hashMap.entrySet()) {
				int[] tmp = entry.getValue();
				if (i >= tmp[0] && i <= tmp[1] || i >= tmp[2] && i <= tmp[3]) {
					return true;
				}
			}
			return false;
		}
	}
}
