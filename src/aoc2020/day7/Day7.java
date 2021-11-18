package aoc2020.day7;

import lombok.Data;
import utils.Day;

import java.util.ArrayList;

public class Day7 extends Day {

	private final ArrayList<Bag> bags;

	public Day7() {
		super(2020, 7);

		this.bags = new ArrayList<>();

		this.example.forEach(s -> {
			String[] line = s.split("contain");
			String color = line[0].split("bags")[0].trim();
			ArrayList<Bag> containingBags = new ArrayList<>();
			if (!line[1].trim().matches("no other bags.")) {
				String[] containingBagsString = line[1].split(",");
				for (String s1 : containingBagsString) {
					String[] containingBag = s1.trim().split("\\s");
					String containingBagColor = containingBag[1].trim() + " " + containingBag[2].trim();
					containingBags.add(new Bag(containingBagColor, null, Integer.parseInt(containingBag[0])));
				}
			}
			Bag tmpBag = new Bag(color, containingBags, 1);
			boolean parentBag = true;
			for (Bag bag : this.bags) {
				parentBag = bag.overrideIfContaining(tmpBag);
			}
			if (parentBag) {
				this.bags.add(tmpBag);
			}
		});
		for (Bag bag : this.bags) {
			System.out.println(bag.toString());
		}
	}

	@Override
	public String resultPartOne() {
		int counter = 0;
		for (Bag bag : this.bags) {
			if (bag.canHoldShinyGoldBag()) {
				counter++;
			}
		}
		return String.valueOf(counter);
	}

	@Override
	public String resultPartTwo() {
		return null;
	}

	@Override
	public int number() {
		return 7;
	}

	@Data
	private static class Bag {
		private final String color;
		private final int numberOfBags;
		private ArrayList<Bag> bags;

		Bag(String color, ArrayList<Bag> bags, int numberOfBags) {
			this.color = color;
			this.bags = bags;
			this.numberOfBags = numberOfBags;
		}

		boolean overrideIfContaining(Bag bag) {
			if (this.color.equals(bag.color)) {
				this.bags = bag.bags;
				return true;
			} else if (this.bags == null || this.bags.isEmpty()) {
				return false;
			} else {
				for (Bag bag1 : this.bags) {
					return bag1.overrideIfContaining(bag);
				}
			}
			return false;
		}

		boolean canHoldShinyGoldBag() {
			if (this.bags == null || this.bags.isEmpty()) {
				return false;
			} else {
				for (Bag bag : this.bags) {
					if (bag.color.equals("shiny gold")) {
						return true;
					} else {
						return bag.canHoldShinyGoldBag();
					}
				}
			}
			return false;
		}
	}
}
