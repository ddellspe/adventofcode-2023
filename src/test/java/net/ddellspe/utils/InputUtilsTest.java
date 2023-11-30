package net.ddellspe.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class InputUtilsTest {
  @Test
  public void testNumberGrid() {
    Map<Point, String> points =
        IntStream.range(1, 10)
            .boxed()
            .collect(Collectors.toMap(v -> new Point((v - 1) % 3, (v - 1) / 3), String::valueOf));
    assertEquals(points, InputUtils.pointStringMap("number_grid.txt", InputUtils.class));
  }

  @Test
  public void testStringGrid() {
    Map<Point, String> points =
        IntStream.range(1, 10)
            .boxed()
            .collect(
                Collectors.toMap(
                    v -> new Point((v - 1) % 3, (v - 1) / 3), v -> v % 2 == 0 ? "#" : "."));
    assertEquals(points, InputUtils.pointStringMap("letter_grid.txt", InputUtils.class));
  }

  @Test
  public void testMaxPoint() {
    List<Point> points =
        IntStream.range(1, 10)
            .boxed()
            .map(v -> new Point(v * 2, v * 4))
            .collect(Collectors.toList());
    assertEquals(18, InputUtils.maxX(points));
    assertEquals(36, InputUtils.maxY(points));
    assertEquals(2, InputUtils.minX(points));
    assertEquals(4, InputUtils.minY(points));
  }

  @Test
  public void testNumbersInLine() {
    List<Integer> values = IntStream.range(1, 11).boxed().collect(Collectors.toList());
    assertEquals(values, InputUtils.numbersInOneLine("numbers_on_one_line.txt", InputUtils.class));
  }

  @Test
  public void testNumberPerLine() {
    List<Integer> values = IntStream.range(1, 11).boxed().collect(Collectors.toList());
    assertEquals(values, InputUtils.numberPerLine("number_per_line.txt", InputUtils.class));
  }

  @Test
  public void testStringPerLine() {
    List<String> values =
        IntStream.range(1, 6).boxed().map(String::valueOf).collect(Collectors.toList());
    assertEquals(values, InputUtils.stringPerLine("string_per_line.txt", InputUtils.class));
  }

  @Test
  public void testFileNotFound() {
    assertThrows(
        NullPointerException.class,
        () -> {
          InputUtils.numbersInOneLine("file_not_found.txt", InputUtils.class);
        });
    assertThrows(
        NullPointerException.class,
        () -> {
          InputUtils.numberPerLine("file_not_found.txt", InputUtils.class);
        });
    assertThrows(
        NullPointerException.class,
        () -> {
          InputUtils.stringPerLine("file_not_found.txt", InputUtils.class);
        });
  }
}
