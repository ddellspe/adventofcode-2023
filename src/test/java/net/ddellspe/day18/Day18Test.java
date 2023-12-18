package net.ddellspe.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day18Test {
  Day18Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(62L, Day18.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 18 Part 1 Answer is: " + Day18.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(952408144115L, Day18.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 18 Part 2 Answer is: " + Day18.part2("input.txt"));
  }
}
