package net.ddellspe.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.ddellspe.day12.Day12.State;
import org.junit.jupiter.api.Test;

public class Day12Test {
  Day12Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(21L, Day12.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 12 Part 1 Answer is: " + Day12.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(525152L, Day12.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 12 Part 2 Answer is: " + Day12.part2("input.txt"));
  }

  @Test
  public void stateTests() {
    State originalState = new State("blah", List.of(1, 2));

    assertTrue(originalState.equals(originalState));
    assertFalse(originalState.equals(null));
    assertFalse(originalState.equals("blah 1,2"));
    assertTrue(originalState.equals(new State("blah", List.of(1, 2))));
    assertFalse(originalState.equals(new State("blah", List.of(1, 3))));
    assertFalse(originalState.equals(new State("blad", List.of(1, 2))));
  }
}
