package net.ddellspe.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day19Test {
  Day19Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(19114L, Day19.part1("example.txt"));
  }

  @Test
  public void exampleInput2TestPart1() {
    assertEquals(0L, Day19.part1("example2.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 19 Part 1 Answer is: " + Day19.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(167409079868000L, Day19.part2("example.txt"));
  }

  @Test
  public void exampleInput3TestPart2() {
    assertEquals(184000000000000L, Day19.part2("example3.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 19 Part 2 Answer is: " + Day19.part2("input.txt"));
  }

  @Test
  public void rangeTests() {
    Day19.Range testRange = new Day19.Range(10, 100);
    assertEquals(0, testRange.getMoreRange(200).start);
    assertEquals(0, testRange.getMoreRange(200).end);
    assertEquals(10, testRange.getMoreRange(0).start);
    assertEquals(100, testRange.getMoreRange(0).end);
    assertEquals(10, testRange.getMoreRemaining(200).start);
    assertEquals(100, testRange.getMoreRemaining(200).end);
    assertEquals(0, testRange.getMoreRemaining(0).start);
    assertEquals(0, testRange.getMoreRemaining(0).end);
    assertEquals(10, testRange.getLessRange(200).start);
    assertEquals(100, testRange.getLessRange(200).end);
    assertEquals(0, testRange.getLessRange(0).start);
    assertEquals(0, testRange.getLessRange(0).end);
    assertEquals(0, testRange.getLessRemaining(200).start);
    assertEquals(0, testRange.getLessRemaining(200).end);
    assertEquals(10, testRange.getLessRemaining(0).start);
    assertEquals(100, testRange.getLessRemaining(0).end);
  }
}
