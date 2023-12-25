package net.ddellspe.day25;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Day25Test {
  Day25Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(54L, Day25.part1("example.txt"));
  }

  @Test
  @Disabled("Takes too long to run")
  public void solutionPart1() {
    System.out.println("Day 25 Part 1 Answer is: " + Day25.part1("input.txt"));
  }

  @Test
  public void connectionTests() {
    Day25.Connection conn = new Day25.Connection("a", "b");
    assertTrue(conn.equals(conn));
    assertFalse(conn.equals(null));
    assertFalse(conn.equals(""));
    assertTrue(conn.equals(new Day25.Connection("a", "b")));
  }
}
