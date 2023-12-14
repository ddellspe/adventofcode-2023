package net.ddellspe.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day14Test {
  Day14Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(136L, Day14.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 14 Part 1 Answer is: " + Day14.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(64L, Day14.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 14 Part 2 Answer is: " + Day14.part2("input.txt"));
  }
}
