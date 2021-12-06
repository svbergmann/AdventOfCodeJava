package aoc2021.day6;

import utils.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Day6 extends Day {

    public Day6() {
        super(2021, 6);
    }

    @Override
    public String resultPartOne() {
        var list = new ArrayList<>(Arrays.stream(this.input.get(0).split(",")).map(s -> new Lanternfish(Integer.parseInt(s))).toList());
        for (var i = 0; i < 80; i++) {
            var appendList = new ArrayList<Lanternfish>();
            for (var l : list) {
                if (l.dayPassed()) appendList.add(new Lanternfish(8));
            }
            list.addAll(appendList);
        }
        return list.size() + "";
    }

    @Override
    public String resultPartTwo() {
        var listOfLists = new HashSet<HashSet<Lanternfish>>();
        listOfLists.add(new HashSet<>(Arrays.stream(this.input.get(0).split(",")).map(s -> new Lanternfish(Integer.parseInt(s))).toList()));
        for (var i = 0; i < 256; i++) {
            var appendLists = new HashSet<HashSet<Lanternfish>>();
            for (var list : listOfLists) {
                var appendList = new HashSet<Lanternfish>();
                for (var l : list) {
                    if (l.dayPassed()) appendList.add(new Lanternfish(8));
                }
                appendLists.add(appendList);
            }
            listOfLists.addAll(appendLists);
        }
        var res = 0;
        for (var list : listOfLists) {
            res += list.size();
        }
        return res + "";
    }

    private static class Lanternfish {
        private int internalTimer;

        public Lanternfish(int internalTimer) {
            this.internalTimer = internalTimer;
        }

        /**
         * Simulates a passed Day.
         *
         * @return true if a new fish is created, else false
         */
        public boolean dayPassed() {
            this.internalTimer--;
            if (this.internalTimer == -1) {
                this.internalTimer = 6;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return this.internalTimer + "";
        }
    }
}
