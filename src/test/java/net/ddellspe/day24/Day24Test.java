package net.ddellspe.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day24Test {
  Day24Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(2L, Day24.part1("example.txt", 7L, 27L));
  }

  @Test
  public void solutionPart1() {
    System.out.println(
        "Day 24 Part 1 Answer is: " + Day24.part1("input.txt", 200000000000000L, 400000000000000L));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(47L, Day24.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 24 Part 2 Answer is: " + Day24.part2("input.txt"));
  }
}
