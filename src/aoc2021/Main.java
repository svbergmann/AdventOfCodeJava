package aoc2021;

import aoc2021.day21.Day21;
import aoc2021.day22.Day22;
import org.jetbrains.annotations.NotNull;
import utils.Day;
import utils.Utilities;

public class Main {

    public static void main(String[] args) {
        printResults(new Day22());
    }

    public static void printResults(@NotNull Day day) {
        System.out.println("Result of day " + day.number + ":");
        var time = System.currentTimeMillis();
        var result = day.resultPartOne();
        var diff = System.currentTimeMillis() - time;
        System.out.println(Utilities.PART_ONE + result);
        System.out.println("Computing took " + diff + " milliseconds.");
        time = System.currentTimeMillis();
        result = day.resultPartTwo();
        diff = System.currentTimeMillis() - time;
        System.out.println(Utilities.PART_TWO + result);
        System.out.println("Computing took " + diff + " milliseconds.");
    }
}
