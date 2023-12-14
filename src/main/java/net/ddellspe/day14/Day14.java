package net.ddellspe.day14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day14 {
  private Day14() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day14.class);
    Set<Point> roundRocks = new HashSet<>();
    Set<Point> cubedRocks = new HashSet<>();
    List<Integer> minimumY =
        new java.util.ArrayList<>(lines.get(0).chars().map(a -> 0).boxed().toList());
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == 'O') {
          roundRocks.add(new Point(x, minimumY.get(x)));
          minimumY.set(x, minimumY.get(x) + 1);
        } else if (line.charAt(x) == '#') {
          cubedRocks.add(new Point(x, y));
          minimumY.set(x, y + 1);
        }
      }
    }
    long maxY =
        Math.max(
            roundRocks.stream().mapToLong(Point::getY).max().getAsLong(),
            cubedRocks.stream().mapToLong(Point::getY).max().getAsLong());
    long sum = 0L;
    for (int y = 0; y <= maxY; y++) {
      int finalY = y;
      sum += (maxY + 1 - y) * roundRocks.stream().filter(p -> p.getY() == finalY).count();
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day14.class);
    Set<Point> roundRocks = new HashSet<>();
    Set<Point> cubedRocks = new HashSet<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == 'O') {
          roundRocks.add(new Point(x, y));
        } else if (line.charAt(x) == '#') {
          cubedRocks.add(new Point(x, y));
        }
      }
    }
    int maxY =
        Math.max(
            roundRocks.stream().mapToLong(Point::getY).mapToInt(a -> (int) a).max().getAsInt(),
            cubedRocks.stream().mapToLong(Point::getY).mapToInt(a -> (int) a).max().getAsInt());
    int maxX =
        Math.max(
            roundRocks.stream().mapToLong(Point::getX).mapToInt(a -> (int) a).max().getAsInt(),
            cubedRocks.stream().mapToLong(Point::getX).mapToInt(a -> (int) a).max().getAsInt());
    Map<Set<Point>, Long> stateCounts = new HashMap<>();
    List<Long> sums = new ArrayList<>();
    long repeatLength;
    for (long count = 0; true; count++) {
      List<Integer> minimumYs =
          new ArrayList<>(IntStream.rangeClosed(0, maxX).map(a -> 0).boxed().toList());
      Set<Point> newRoundsRocks = new HashSet<>();
      for (int y = 0; y <= maxY; y++) {
        for (int x = 0; x <= maxX; x++) {
          if (roundRocks.contains(new Point(x, y))) {
            newRoundsRocks.add(new Point(x, minimumYs.get(x)));
            minimumYs.set(x, minimumYs.get(x) + 1);
          } else if (cubedRocks.contains(new Point(x, y))) {
            minimumYs.set(x, y + 1);
          }
        }
      }
      roundRocks = new HashSet<>(newRoundsRocks);
      newRoundsRocks = new HashSet<>();
      List<Integer> minimumXs =
          new ArrayList<>(IntStream.rangeClosed(0, maxY).map(a -> 0).boxed().toList());
      for (int x = 0; x <= maxX; x++) {
        for (int y = 0; y <= maxY; y++) {
          if (roundRocks.contains(new Point(x, y))) {
            newRoundsRocks.add(new Point(minimumXs.get(y), y));
            minimumXs.set(y, minimumXs.get(y) + 1);
          } else if (cubedRocks.contains(new Point(x, y))) {
            minimumXs.set(y, x + 1);
          }
        }
      }
      roundRocks = new HashSet<>(newRoundsRocks);
      newRoundsRocks = new HashSet<>();
      minimumYs = new ArrayList<>(IntStream.rangeClosed(0, maxX).map(a -> maxX).boxed().toList());
      for (int y = maxY; y >= 0; y--) {
        for (int x = 0; x <= maxX; x++) {
          if (roundRocks.contains(new Point(x, y))) {
            newRoundsRocks.add(new Point(x, minimumYs.get(x)));
            minimumYs.set(x, minimumYs.get(x) - 1);
          } else if (cubedRocks.contains(new Point(x, y))) {
            minimumYs.set(x, y - 1);
          }
        }
      }
      roundRocks = new HashSet<>(newRoundsRocks);
      newRoundsRocks = new HashSet<>();
      minimumXs = new ArrayList<>(IntStream.rangeClosed(0, maxY).map(a -> maxY).boxed().toList());
      for (int x = maxY; x >= 0; x--) {
        for (int y = 0; y <= maxY; y++) {
          if (roundRocks.contains(new Point(x, y))) {
            newRoundsRocks.add(new Point(minimumXs.get(y), y));
            minimumXs.set(y, minimumXs.get(y) - 1);
          } else if (cubedRocks.contains(new Point(x, y))) {
            minimumXs.set(y, x - 1);
          }
        }
      }
      roundRocks = new HashSet<>(newRoundsRocks);
      long sum = 0L;
      for (int y = 0; y <= maxY; y++) {
        int finalY = y;
        sum += (maxY + 1 - y) * roundRocks.stream().filter(p -> p.getY() == finalY).count();
      }
      sums.add(sum);
      if (stateCounts.containsKey(roundRocks)) {
        repeatLength = count - stateCounts.get(roundRocks);
        break;
      }
      stateCounts.put(roundRocks, count);
    }
    long stateCount = stateCounts.get(roundRocks);
    long modularCount = stateCount + (1000000000L - stateCount) % repeatLength - 1;
    return sums.get((int) modularCount);
  }
}
