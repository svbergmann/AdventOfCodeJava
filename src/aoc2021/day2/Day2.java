package aoc2021.day2;

import utils.Day;

public class Day2 extends Day {

    public Day2() {
        super(2021, 2);
    }

    @Override
    public String resultPartOne() {
        return this.computePos(true) + "";
    }

    @Override
    public String resultPartTwo() {
        return this.computePos(false) + "";
    }

    private int computePos(boolean first) {
        var horizontal = 0;
        var depth = 0;
        var aim = 0;
        for (var s : this.input) {
            var value = Integer.parseInt(s.substring(s.length() - 1));
            if (s.startsWith("forward")) {
                horizontal += value;
                if (!first) depth += aim * value;
            } else if (s.startsWith("down")) {
                if (first) depth += value;
                else aim += value;
            } else if (s.startsWith("up")) {
                if (first) depth -= value;
                else aim -= value;
            }
        }
        return horizontal * depth;
    }
}
