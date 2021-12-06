package aoc2021.day5;

import utils.Day;
import utils.Utilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Day5 extends Day {

    private final int[][] system;

    public Day5() {
        super(2021, 5);
        var max = this.getMaxNum(false);
        this.system = new int[max + 1][max + 1];
        this.setHorizontalLines(false);
    }

    @Override
    public String resultPartOne() {
        var res = 0;
        for (var i : this.system) {
            for (var j : i) {
                if (j >= 2) res++;
            }
        }
        return res + "";
    }

    @Override
    public String resultPartTwo() {
        this.setDiagonalLines(false);
        var res = 0;
        for (var i : this.system) {
            for (var j : i) {
                if (j >= 2) res++;
            }
        }
        return res + "";
    }

    private int getMaxNum(boolean example) {
        HashSet<Integer> setOfNumbers;
        if (example) {
            setOfNumbers = this.example.stream()
                    .flatMap(s -> Arrays.stream(s.split(" -> ")))
                    .flatMap(i -> Arrays.stream(i.split(",")))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(HashSet::new)
                    );
        } else {
            setOfNumbers = this.input.stream()
                    .flatMap(s -> Arrays.stream(s.split(" -> ")))
                    .flatMap(i -> Arrays.stream(i.split(",")))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(HashSet::new)
                    );
        }
        return setOfNumbers.stream().max(Integer::compareTo).get();
    }

    private void setHorizontalLines(boolean example) {
        var list = example ? this.example : this.input;
        for (var s : list) {
            var s1 = s.split(" -> ");
            var x1 = Integer.parseInt(s1[0].split(",")[0]);
            var y1 = Integer.parseInt(s1[0].split(",")[1]);
            var x2 = Integer.parseInt(s1[1].split(",")[0]);
            var y2 = Integer.parseInt(s1[1].split(",")[1]);
            if (x1 == x2) {
                if (y1 > y2) {
                    var tmp = y1;
                    y1 = y2;
                    y2 = tmp;
                }
                for (var i = y1; i <= y2; i++) {
                    this.system[i][x1]++;
                }
            }
            if (y1 == y2) {
                if (x1 > x2) {
                    var tmp = x1;
                    x1 = x2;
                    x2 = tmp;
                }
                for (var i = x1; i <= x2; i++) {
                    this.system[y1][i]++;
                }
            }
        }
    }

    private void setDiagonalLines(boolean example) {
        var list = example ? this.example : this.input;
        for (var s : list) {
            var s1 = s.split(" -> ");
            var x1 = Integer.parseInt(s1[0].split(",")[0]);
            var y1 = Integer.parseInt(s1[0].split(",")[1]);
            var x2 = Integer.parseInt(s1[1].split(",")[0]);
            var y2 = Integer.parseInt(s1[1].split(",")[1]);
            if (Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
                if (x1 < x2) {
                    if (y1 < y2) {
                        for (var x = x1; x <= x2; x++) {
                            this.system[y1][x]++;
                            y1++;
                        }
                    } else {
                        for (var x = x1; x <= x2; x++) {
                            this.system[y1][x]++;
                            y1--;
                        }
                    }
                } else if (x1 > x2) {
                    if (y1 < y2) {
                        for (var x = x1; x >= x2; x--) {
                            this.system[y1][x]++;
                            y1++;
                        }
                    } else {
                        for (var x = x1; x >= x2; x--) {
                            this.system[y1][x]++;
                            y1--;
                        }
                    }
                } else {
                    var t1 = Math.min(x1, y1);
                    var t2 = Math.max(x1, y2);
                    for (var x = t1; x <= t2; x++) {
                        this.system[x][x]++;
                    }
                }
            }
        }
    }
}
