package net.ddellspe.day21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day21 {
  private Day21() {}

  public static long part1(String filename, int steps) {
    List<String> lines = InputUtils.stringPerLine(filename, Day21.class);
    Set<Point> gardenPlots = new HashSet<>();
    Set<Point> rocks = new HashSet<>();
    Point startPoint = null;
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        Point pt = new Point(x, y);
        if (line.charAt(x) == '.') {
          gardenPlots.add(pt);
        } else if (line.charAt(x) == '#') {
          rocks.add(pt);
        } else {
          gardenPlots.add(pt);
          startPoint = pt;
        }
      }
    }
    Set<Point> points = new HashSet<>();
    points.add(startPoint);
    for (int i = 0; i < steps; i++) {
      Set<Point> nextIteration = new HashSet<>();
      for (Point point : points) {
        nextIteration.addAll(
            point.getDirectNeighbors().stream()
                .filter(gardenPlots::contains)
                .collect(Collectors.toSet()));
      }
      points = nextIteration;
    }
    return points.size();
  }

  public static long part2(String filename, int steps) {
    List<String> lines = InputUtils.stringPerLine(filename, Day21.class);
    Set<Point> gardenPlots = new HashSet<>();
    Set<Point> rocks = new HashSet<>();
    Point startPoint = null;
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        Point pt = new Point(x, y);
        if (line.charAt(x) == '.') {
          gardenPlots.add(pt);
        } else if (line.charAt(x) == '#') {
          rocks.add(pt);
        } else {
          gardenPlots.add(pt);
          startPoint = pt;
        }
      }
    }
    Set<Point> points = new HashSet<>();
    points.add(startPoint);
    long xMax = gardenPlots.stream().mapToLong(Point::getX).max().getAsLong() + 1L;
    long yMax = gardenPlots.stream().mapToLong(Point::getY).max().getAsLong() + 1L;
    List<Integer> values = new ArrayList<>();
    int i = 1;
    while (values.size() < 3 && i <= steps) {
      Set<Point> nextIteration = new HashSet<>();
      for (Point point : points) {
        nextIteration.addAll(
            point.getDirectNeighbors().stream()
                .filter(
                    p ->
                        gardenPlots.contains(
                            new Point(
                                Math.floorMod(p.getX(), xMax), Math.floorMod(p.getY(), yMax))))
                .collect(Collectors.toSet()));
      }
      points = nextIteration;
      if (i % xMax == steps % xMax) {
        values.add(points.size());
      }
      i++;
    }
    long value;
    if (values.size() == 3) {
      long b0 = values.get(0);
      long b1 = values.get(1) - values.get(0);
      long b2 = values.get(2) - values.get(1);
      long n = steps / xMax;
      value = b0 + n * (b1 + (n - 1) * (b2 - b1) / 2);
    } else {
      value = points.size();
    }
    return value;
  }
}
