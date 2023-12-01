package net.ddellspe.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day01Test {
  Day01Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(142L, Day01.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 01 Part 1 Answer is: " + Day01.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(281L, Day01.part2("example2.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 01 Part 2 Answer is: " + Day01.part2("input.txt"));
  }
}
