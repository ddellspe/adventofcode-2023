package net.ddellspe.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Day23Test {
  Day23Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(94L, Day23.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 23 Part 1 Answer is: " + Day23.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(154L, Day23.part2("example.txt"));
  }

  @Test
  @Disabled("doesn't run on CI")
  public void solutionPart2() {
    System.out.println("Day 23 Part 2 Answer is: " + Day23.part2("input.txt"));
  }
}
