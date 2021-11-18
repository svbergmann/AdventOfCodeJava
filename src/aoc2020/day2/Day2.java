package aoc2020.day2;

import utils.Day;

import java.util.concurrent.atomic.AtomicInteger;

public class Day2 extends Day {

  public Day2() {
    super(2020, 2);
  }

  @Override
  public String resultPartOne() {
    AtomicInteger counter = new AtomicInteger(0);
    this.input.forEach(
        s -> {
          // Split at "-" for the start index
          int start = Integer.parseInt(s.split("-")[0]);
          // Split at "-" then at " " for the end index
          int end = Integer.parseInt(s.split("-")[1].split("\\s")[0]);
          // Split at " " then at ":" for the character
          char character = s.split("\\s")[1].split(":")[0].toCharArray()[0];

          char[] sequence = s.split(":")[1].toCharArray();
          int count = 0;
          for (char c : sequence) {
            if (c == character) {
              count++;
            }
          }
          if (count >= start && count <= end) {
            counter.incrementAndGet();
          }
        });
    return counter.toString();
  }

  @Override
  public String resultPartTwo() {
    AtomicInteger counter = new AtomicInteger(0);
    this.input.forEach(
        s -> {
          // Split at "-" for the start index
          int start = Integer.parseInt(s.split("-")[0]) - 1;
          // Split at "-" then at " " for the end index
          int end = Integer.parseInt(s.split("-")[1].split("\\s")[0]) - 1;
          // Split at " " then at ":" for the character
          char character = s.split("\\s")[1].split(":")[0].toCharArray()[0];

          char[] sequence = s.split(":")[1].trim().toCharArray();
          boolean contains = false;
          if (start < sequence.length) {
            if (sequence[start] == character) {
              contains = true;
            }
          }
          if (end < sequence.length) {
            if (sequence[end] == character) {
              contains = !contains;
            }
          }
          if (contains) {
            counter.incrementAndGet();
          }
        });
    return counter.toString();
  }

  @Override
  public int number() {
    return 2;
  }
}
