package net.ddellspe.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day20Test {
  Day20Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(32000000L, Day20.part1("example.txt"));
  }

  @Test
  public void providedInput2TestPart1() {
    assertEquals(11687500L, Day20.part1("example2.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 20 Part 1 Answer is: " + Day20.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(2L, Day20.part2("example3.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 20 Part 2 Answer is: " + Day20.part2("input.txt"));
  }
}
