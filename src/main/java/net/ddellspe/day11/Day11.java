package net.ddellspe.day11;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day11 {
  private Day11() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day11.class);
    Set<Point> galaxies = new HashSet<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          galaxies.add(new Point(x, y));
        }
      }
    }
    List<Long> usedX =
        galaxies.stream().mapToLong(Point::getX).distinct().sorted().boxed().toList();
    List<Long> usedY =
        galaxies.stream().mapToLong(Point::getY).distinct().sorted().boxed().toList();
    for (long x = usedX.get(usedX.size() - 1); x >= 0; x--) {
      if (usedX.contains(x)) {
        continue;
      }
      long finalX = x;
      galaxies =
          galaxies.stream()
              .map(p -> p.getX() > finalX ? new Point(p.getX() + 1, p.getY()) : p)
              .collect(Collectors.toSet());
    }
    for (long y = usedY.get(usedY.size() - 1); y >= 0; y--) {
      if (usedY.contains(y)) {
        continue;
      }
      long finalY = y;
      galaxies =
          galaxies.stream()
              .map(p -> p.getY() > finalY ? new Point(p.getX(), p.getY() + 1) : p)
              .collect(Collectors.toSet());
    }
    long sum = 0L;
    Set<Set<Point>> visitedPairs = new HashSet<>();
    for (Point pt1 : galaxies) {
      for (Point pt2 : galaxies) {
        if (pt1.equals(pt2)) {
          continue;
        }
        if (!visitedPairs.contains(Set.of(pt1, pt2))) {
          sum += pt1.getManhattanDistance(pt2);
          visitedPairs.add(Set.of(pt1, pt2));
        }
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day11.class);
    long factor = 999999L;
    Set<Point> galaxies = new HashSet<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          galaxies.add(new Point(x, y));
        }
      }
    }
    List<Long> usedX =
        galaxies.stream().mapToLong(Point::getX).distinct().sorted().boxed().toList();
    List<Long> usedY =
        galaxies.stream().mapToLong(Point::getY).distinct().sorted().boxed().toList();
    for (long x = usedX.get(usedX.size() - 1); x >= 0; x--) {
      if (usedX.contains(x)) {
        continue;
      }
      long finalX = x;
      galaxies =
          galaxies.stream()
              .map(p -> p.getX() > finalX ? new Point(p.getX() + factor, p.getY()) : p)
              .collect(Collectors.toSet());
    }
    for (long y = usedY.get(usedY.size() - 1); y >= 0; y--) {
      if (usedY.contains(y)) {
        continue;
      }
      long finalY = y;
      galaxies =
          galaxies.stream()
              .map(p -> p.getY() > finalY ? new Point(p.getX(), p.getY() + factor) : p)
              .collect(Collectors.toSet());
    }
    long sum = 0L;
    Set<Set<Point>> visitedPairs = new HashSet<>();
    for (Point pt1 : galaxies) {
      for (Point pt2 : galaxies) {
        if (pt1.equals(pt2)) {
          continue;
        }
        if (!visitedPairs.contains(Set.of(pt1, pt2))) {
          sum += pt1.getManhattanDistance(pt2);
          visitedPairs.add(Set.of(pt1, pt2));
        }
      }
    }
    return sum;
  }
}
