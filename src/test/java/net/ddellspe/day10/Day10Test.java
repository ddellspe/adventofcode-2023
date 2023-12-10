package net.ddellspe.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day10Test {
  Day10Test() {}

  @Test
  public void providedInput1TestPart1() {
    assertEquals(4L, Day10.part1("example.txt"));
  }

  @Test
  public void providedInputFTestPart1() {
    assertEquals(4L, Day10.part1("example_F.txt"));
  }

  @Test
  public void providedInput7TestPart1() {
    assertEquals(4L, Day10.part1("example_7.txt"));
  }

  @Test
  public void providedInputLTestPart1() {
    assertEquals(4L, Day10.part1("example_L.txt"));
  }

  @Test
  public void providedInputJTestPart1() {
    assertEquals(4L, Day10.part1("example_J.txt"));
  }

  @Test
  public void providedInputVerticalBarTestPart1() {
    assertEquals(4L, Day10.part1("example_verticalBar.txt"));
  }

  @Test
  public void providedInputHyphenTestPart1() {
    assertEquals(4L, Day10.part1("example_hyphen.txt"));
  }

  @Test
  public void providedInput2TestPart1() {
    assertEquals(8L, Day10.part1("example2.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 10 Part 1 Answer is: " + Day10.part1("input.txt"));
  }

  @Test
  public void providedInput1TestPart2() {
    assertEquals(1L, Day10.part2("example.txt"));
  }

  @Test
  public void providedInputFTestPart2() {
    assertEquals(1L, Day10.part2("example_F.txt"));
  }

  @Test
  public void providedInput7TestPart2() {
    assertEquals(1L, Day10.part2("example_7.txt"));
  }

  @Test
  public void providedInputLTestPart2() {
    assertEquals(1L, Day10.part2("example_L.txt"));
  }

  @Test
  public void providedInputJTestPart2() {
    assertEquals(1L, Day10.part2("example_J.txt"));
  }

  @Test
  public void providedInputVerticalBarTestPart2() {
    assertEquals(1L, Day10.part2("example_verticalBar.txt"));
  }

  @Test
  public void providedInputHyphenTestPart2() {
    assertEquals(1L, Day10.part2("example_hyphen.txt"));
  }

  @Test
  public void providedInput2TestPart2() {
    assertEquals(1L, Day10.part2("example2.txt"));
  }

  @Test
  public void providedInput3TestPart2() {
    assertEquals(4L, Day10.part2("example3.txt"));
  }

  @Test
  public void providedInput4TestPart2() {
    assertEquals(8L, Day10.part2("example4.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 10 Part 2 Answer is: " + Day10.part2("input.txt"));
  }
}
