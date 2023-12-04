package net.ddellspe.day04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day04Test {
  Day04Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(13L, Day04.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 04 Part 1 Answer is: " + Day04.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(30L, Day04.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 04 Part 2 Answer is: " + Day04.part2("input.txt"));
  }
}
