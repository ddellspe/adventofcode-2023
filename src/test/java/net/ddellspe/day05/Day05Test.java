package net.ddellspe.day05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ddellspe.day05.Day05.Range;
import org.junit.jupiter.api.Test;

public class Day05Test {
  Day05Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(35L, Day05.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 05 Part 1 Answer is: " + Day05.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(46L, Day05.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 05 Part 2 Answer is: " + Day05.part2("input.txt"));
  }

  @Test
  public void coverageTest() {
    Range range = new Range(2L, 10L);
    assertTrue(range.overlapOrAdjacent(new Range(0L, 2L)));
    assertTrue(range.overlapOrAdjacent(new Range(10L, 11L)));
    assertTrue(range.overlapOrAdjacent(new Range(3L, 8L)));
    assertFalse(range.overlapOrAdjacent(new Range(0L, 1L)));
  }
}
