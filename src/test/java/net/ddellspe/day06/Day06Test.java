package net.ddellspe.day06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day06Test {
  Day06Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(288L, Day06.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 06 Part 1 Answer is: " + Day06.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(71503L, Day06.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 06 Part 2 Answer is: " + Day06.part2("input.txt"));
  }
}
