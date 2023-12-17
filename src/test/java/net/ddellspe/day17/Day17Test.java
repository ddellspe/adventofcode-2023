package net.ddellspe.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ddellspe.day17.Day17.Direction;
import net.ddellspe.day17.Day17.State;
import net.ddellspe.utils.Point;
import org.junit.jupiter.api.Test;

public class Day17Test {
  Day17Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(102L, Day17.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 17 Part 1 Answer is: " + Day17.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(94L, Day17.part2("example.txt"));
  }

  @Test
  public void providedInput2TestPart2() {
    assertEquals(71L, Day17.part2("example2.txt"));
  }

  @Test
  public void providedInput3TestPart2() {
    assertEquals(Long.MAX_VALUE, Day17.part2("example3.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 17 Part 2 Answer is: " + Day17.part2("input.txt"));
  }

  @Test
  public void StateTest() {
    State state = new State(new Point(0, 0), Direction.UP, 0, 0);

    assertTrue(state.equals(state));
    assertFalse(state.equals(null));
    assertFalse(state.equals(""));
    assertTrue(state.equals(new State(new Point(0, 0), Direction.UP, 0, 0)));
    assertFalse(state.equals(new State(new Point(0, 0), Direction.UP, 1, 0)));
    assertFalse(state.equals(new State(new Point(0, 0), Direction.UP, 0, 1)));
    assertFalse(state.equals(new State(new Point(0, 0), Direction.DOWN, 0, 0)));
    assertFalse(state.equals(new State(new Point(0, 1), Direction.UP, 0, 0)));
  }
}
