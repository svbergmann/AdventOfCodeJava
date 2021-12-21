package aoc2020.day7;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.HashMap;

public class Day7 extends Day {

    private final ArrayList<Bag> bags;

    public Day7() {
        super(2020, 7);
        this.bags = new ArrayList<>();

        for (var s : this.input) {
            this.bags.add(
                    new Bag(s.split("contain")[0].split("bags")[0].trim(), null)
            );
        }

        for (var s : this.input) {
            if (!s.split("contain")[1].trim()
                    .matches("no other bags.")) {
                var parentBagColor = s.split("contain")[0].split("bags")[0].trim();
                var containingBagsString = s.split("contain")[1].split(",");
                for (var s1 : containingBagsString) {
                    var containingBag = s1.trim()
                            .split("\\s");
                    var numberOfBags = Integer.parseInt(containingBag[0].trim()
                            .substring(0, 1));
                    var containingBagColor = containingBag[1].trim() + " " + containingBag[2].trim();
                    var actualBag = this.bags.stream()
                            .filter(bag -> bag.color.equals(containingBagColor))
                            .findFirst()
                            .get();
                    var parentBag = this.bags.stream()
                            .filter(bag -> bag.color.equals(parentBagColor))
                            .findFirst()
                            .get();
                    parentBag.addBag(actualBag, numberOfBags);
                }
            }
        }
    }

    private static int getNumShinyGoldBagsRek(@NotNull Bag bag) {
        var num = 0;
        if (bag.bags.keySet()
                .stream()
                .anyMatch(bag1 -> bag1.color.equals("shiny gold"))) {
            return 1;
        }
        for (var bag1 : bag.bags.keySet()) {
            num += getNumShinyGoldBagsRek(bag1);
        }
        return num;
    }

    @Override
    public String resultPartOne() {
        var counter = 0;
        for (Bag bag : this.bags) {
            var tmp = getNumShinyGoldBagsRek(bag);
            counter += (tmp > 0) ? 1 : 0;
        }
        return String.valueOf(counter);
    }

    @Override
    public String resultPartTwo() {
        var counter = 0;
        for (Bag bag : this.bags) {
            if (bag.color.equals("shiny gold")) {
                counter = bag.getContainingBagsRek();
                break;
            }
        }
        return String.valueOf(counter);
    }

    private record Bag(String color, HashMap<Bag, Integer> bags) {
        private Bag(String color, HashMap<Bag, Integer> bags) {
            this.color = color;
            this.bags = (bags == null) ? new HashMap<>() : bags;
        }

        public void addBag(Bag bag, int numberOfBags) {
            this.bags.put(bag, numberOfBags);
        }

        private int getContainingBagsRek() {
            var num = 0;
            if (this.bags.isEmpty()) {
                return 1;
            }
            for (var entry : this.bags.entrySet()) {
                if (!entry.getKey().bags.isEmpty()) {
                    num += entry.getValue();
                }
                num += entry.getKey()
                        .getContainingBagsRek() * entry.getValue();
            }
            return num;
        }
    }
}
