package net.ddellspe.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day13Test {
  Day13Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(405L, Day13.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 13 Part 1 Answer is: " + Day13.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(400L, Day13.part2("example.txt"));
  }

  @Test
  public void badDataPart2() {
    assertEquals(-1L, Day13.part2("bad_data.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 13 Part 2 Answer is: " + Day13.part2("input.txt"));
  }
}
