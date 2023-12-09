package net.ddellspe.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day09Test {
  Day09Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(114L, Day09.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 09 Part 1 Answer is: " + Day09.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(2L, Day09.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 09 Part 2 Answer is: " + Day09.part2("input.txt"));
  }
}
