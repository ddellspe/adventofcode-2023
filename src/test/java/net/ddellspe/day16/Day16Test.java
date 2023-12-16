package net.ddellspe.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ddellspe.day16.Day16.Beam;
import net.ddellspe.day16.Day16.Direction;
import net.ddellspe.utils.Point;
import org.junit.jupiter.api.Test;

public class Day16Test {
  Day16Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(46L, Day16.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 16 Part 1 Answer is: " + Day16.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(51L, Day16.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 16 Part 2 Answer is: " + Day16.part2("input.txt"));
  }

  @Test
  public void beamTests() {
    Beam beam = new Beam(new Point(0, 0), Direction.RIGHT);

    assertTrue(beam.equals(beam));
    assertFalse(beam.equals(null));
    assertFalse(beam.equals(""));
    assertTrue(beam.equals(new Beam(new Point(0, 0), Direction.RIGHT)));
    assertFalse(beam.equals(new Beam(new Point(1, 0), Direction.RIGHT)));
    assertFalse(beam.equals(new Beam(new Point(0, 0), Direction.LEFT)));
  }
}
