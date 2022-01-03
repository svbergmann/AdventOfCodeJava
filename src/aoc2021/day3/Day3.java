package aoc2021.day3;

import org.jetbrains.annotations.NotNull;
import utils.Day;

import java.util.ArrayList;
import java.util.List;

public class Day3 extends Day {

    public Day3() {
        super(2021, 3);
    }

    @Override
    public String resultPartOne() {
        var gammaRate = new StringBuilder();
        var epsilonRate = new StringBuilder();
        var significantBits = this.getSignificantBits(this.input);
        for (var i = 0; i < this.input.get(1)
                .length(); i++) {
            if (significantBits[i] == '1') {
                gammaRate.append("1");
                epsilonRate.append("0");
            } else {
                gammaRate.append("0");
                epsilonRate.append("1");
            }
        }
        return Integer.parseInt(gammaRate.toString(), 2) * Integer.parseInt(epsilonRate.toString(), 2) + "";
    }

    @Override
    public String resultPartTwo() {
        var binaries = new ArrayList<>(this.input);
        var oxygenGeneratorRating = this.getRemainingIntValue(binaries, true);
        binaries = new ArrayList<>(this.input);
        var co2ScrubberRating = this.getRemainingIntValue(binaries, false);
        return oxygenGeneratorRating + " * " + co2ScrubberRating + " = " + oxygenGeneratorRating * co2ScrubberRating;
    }

    /**
     * Tries to compute the most significant values at each position.
     *
     * @param list the list with the bit values
     * @return a char array with the most common bit at each position or the value 2 if those are equal
     */
    private char @NotNull [] getSignificantBits(@NotNull List<String> list) {
        var lengthOfArray = list.get(1)
                .length();
        var numberOfOneBits = new int[lengthOfArray];
        for (String s : list) {
            for (var j = 0; j < lengthOfArray; j++) {
                if (s.charAt(j) == '1') numberOfOneBits[j]++;
            }
        }
        var significantBitArray = new char[lengthOfArray];
        for (var i = 0; i < numberOfOneBits.length; i++) {
            if (list.size() - numberOfOneBits[i] > numberOfOneBits[i]) {
                significantBitArray[i] = '0';
            }
            if (list.size() - numberOfOneBits[i] < numberOfOneBits[i]) {
                significantBitArray[i] = '1';
            }
            if (list.size() - numberOfOneBits[i] == numberOfOneBits[i]) {
                significantBitArray[i] = '2';
            }
        }
        return significantBitArray;
    }

    private int getRemainingIntValue(List<String> list, boolean mostSignificantBit) {
        char[] significantBits;
        var j = 0;
        boolean sameBit;
        while (j < this.getSignificantBits(list).length) {
            significantBits = this.getSignificantBits(list);
            if (list.size() > 2) {
                var mostCommonBit = significantBits[j];
                sameBit = mostCommonBit == '2';
                for (var i = list.size() - 1; i >= 0; i--) {
                    if (mostSignificantBit) {
                        if (sameBit && list.get(i)
                                .charAt(j) != '1' || list.get(i)
                                .charAt(j) != mostCommonBit) {
                            list.remove(i);
                        }
                    } else if (sameBit && list.get(i)
                            .charAt(j) != '0' || list.get(i)
                            .charAt(j) == mostCommonBit) {
                        list.remove(i);
                    }
                }
            } else if (list.size() == 2) {
                if (list.get(0)
                        .equals(list.get(1))) {
                    list.remove(1);
                } else if (list.get(0)
                        .charAt(j) != list.get(1)
                        .charAt(j)) {
                    for (var i = list.size() - 1; i >= 0; i--) {
                        if (mostSignificantBit) {
                            if (list.get(i)
                                    .charAt(j) != '1') list.remove(i);
                        } else if (list.get(i)
                                .charAt(j) == '1') list.remove(i);
                    }
                }
            }
            if (list.size() == 1) break;
            j++;
        }
        return Integer.parseInt(list.get(0), 2);
    }
}
