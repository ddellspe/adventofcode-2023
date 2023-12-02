package net.ddellspe.day02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day02Test {
  Day02Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(8L, Day02.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 02 Part 1 Answer is: " + Day02.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(2286L, Day02.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 02 Part 2 Answer is: " + Day02.part2("input.txt"));
  }
}
