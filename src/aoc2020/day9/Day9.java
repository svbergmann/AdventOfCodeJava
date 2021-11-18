package aoc2020.day9;

import utils.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 extends Day {

  private final List<Long> numbers;
  private final int lengthOfPreamble;

  public Day9() {
    super(2020, 9);
    this.numbers =
        this.input.stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
    this.lengthOfPreamble = 25;
  }

  @Override
  public String resultPartOne() {
    return String.valueOf(this.checkForWeakness(this.lengthOfPreamble, this.numbers));
  }

  @Override
  public String resultPartTwo() {
    return String.valueOf(
        this.checkForEncryptionWeakness(
            this.checkForWeakness(this.lengthOfPreamble, this.numbers), this.numbers));
  }

  @Override
  public int number() {
    return 9;
  }

  private long checkForWeakness(int lengthOfPreamble, List<Long> numbers) {
    int counter = lengthOfPreamble;
    int indexToBeChecked = lengthOfPreamble + 1;
    boolean found = false;
    while (counter < numbers.size() - 1) {
      for (int i = counter - lengthOfPreamble; i < numbers.size() - 1; i++) {
        for (int j = i + 1; j < numbers.size(); j++) {
          if (numbers.get(i) + numbers.get(j) == numbers.get(indexToBeChecked)) {
            found = true;
            break;
          }
        }
        if (found) {
          break;
        }
      }
      if (!found) {
        return numbers.get(indexToBeChecked);
      }
      found = false;
      counter++;
      indexToBeChecked++;
    }
    return 0;
  }

  private long checkForEncryptionWeakness(long invalidNumber, List<Long> numbers) {
    boolean found = false;
    ArrayList<Long> contiguousSet = new ArrayList<>();
    int firstAddedIndex = 0;
    for (int i = firstAddedIndex; i < numbers.size(); i++) {
      Long number = numbers.get(i);
      contiguousSet.add(number);
      long sum = contiguousSet.stream().mapToLong(Long::longValue).sum();
      if (sum == invalidNumber && contiguousSet.size() >= 2) {
        found = true;
        break;
      }
      if (sum > invalidNumber) {
        contiguousSet.clear();
        firstAddedIndex++;
        i = firstAddedIndex;
      }
    }
    if (found) {
      return contiguousSet.stream().mapToLong(Long::longValue).min().getAsLong()
          + contiguousSet.stream().mapToLong(Long::longValue).max().getAsLong();
    }
    return 0;
  }
}
