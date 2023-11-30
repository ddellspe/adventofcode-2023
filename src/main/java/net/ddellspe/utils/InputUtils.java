package net.ddellspe.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InputUtils {
  private InputUtils() {}

  public static Map<Point, String> pointStringMap(String filename, Class klass) {
    List<String> lines = stringPerLine(filename, klass);
    Map<Point, String> points = new LinkedHashMap<>();
    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(i).length(); j++) {
        points.put(new Point(j, i), String.valueOf(lines.get(i).charAt(j)));
      }
    }
    return points;
  }

  public static long maxX(Collection<Point> pointMap) {
    return pointMap.stream().map(Point::getX).max(Comparator.naturalOrder()).get();
  }

  public static long maxY(Collection<Point> pointMap) {
    return pointMap.stream().map(Point::getY).max(Comparator.naturalOrder()).get();
  }

  public static long minX(Collection<Point> pointMap) {
    return pointMap.stream().map(Point::getX).min(Comparator.naturalOrder()).get();
  }

  public static long minY(Collection<Point> pointMap) {
    return pointMap.stream().map(Point::getY).min(Comparator.naturalOrder()).get();
  }

  public static List<Integer> numberPerLine(String filename, Class klass) {
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(klass.getResourceAsStream(filename))));
    return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
  }

  public static List<Integer> numbersInOneLine(String filename, Class klass) {
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(klass.getResourceAsStream(filename))));
    return Arrays.stream(reader.lines().findFirst().get().split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

  public static List<String> stringPerLine(String filename, Class klass) {
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(klass.getResourceAsStream(filename))));
    return reader.lines().collect(Collectors.toList());
  }
}
