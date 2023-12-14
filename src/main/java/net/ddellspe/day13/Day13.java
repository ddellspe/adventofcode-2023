package net.ddellspe.day13;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day13 {
  private Day13() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day13.class);
    List<Set<Point>> puzzles = new ArrayList<>();
    Set<Point> puzzle = new HashSet<>();
    long y = 0L;
    for (String line : lines) {
      if (line.isEmpty()) {
        puzzles.add(puzzle);
        puzzle = new HashSet<>();
        y = 0L;
        continue;
      }
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          puzzle.add(new Point(x, y));
        }
      }
      y++;
    }
    puzzles.add(puzzle);
    long sum = 0L;
    for (Set<Point> puzz : puzzles) {
      sum += calculateScore(puzz, false);
    }
    return sum;
  }

  public static long calculateScore(Set<Point> puzzle, boolean smudgesPresent) {
    long cols = puzzle.stream().mapToLong(Point::getX).max().getAsLong();
    List<Set<Long>> colData =
        LongStream.rangeClosed(0, cols)
            .mapToObj(
                col ->
                    puzzle.stream()
                        .filter(p -> p.getX() == col)
                        .mapToLong(Point::getY)
                        .boxed()
                        .collect(Collectors.toSet()))
            .toList();
    for (int col = 0; col < cols; col++) {
      boolean mirror = true;
      boolean change = false;
      for (int check = col; check >= 0; check--) {
        if ((col + (col - check + 1)) >= colData.size()) {
          break;
        }
        Set<Long> left = colData.get(check);
        Set<Long> right = colData.get(col + (col - check + 1));
        if (!left.equals(right)) {
          if (!change && smudgesPresent) {
            Set<Long> testSet;
            Set<Long> secondSet;
            if (left.size() > right.size()) {
              testSet = new HashSet<>(left);
              testSet.removeAll(right);
              secondSet = new HashSet<>(right);
              secondSet.removeAll(left);
            } else {
              testSet = new HashSet<>(right);
              testSet.removeAll(left);
              secondSet = new HashSet<>(left);
              secondSet.removeAll(right);
            }
            if (testSet.size() == 1 && secondSet.isEmpty()) {
              change = true;
            } else {
              mirror = false;
              break;
            }
          } else {
            mirror = false;
            break;
          }
        }
      }
      if (mirror && (change || !smudgesPresent)) {
        return (col + 1L);
      }
    }
    long rows = puzzle.stream().mapToLong(Point::getY).max().getAsLong();
    List<Set<Long>> rowData =
        LongStream.rangeClosed(0, rows)
            .mapToObj(
                row ->
                    puzzle.stream()
                        .filter(p -> p.getY() == row)
                        .mapToLong(Point::getX)
                        .boxed()
                        .collect(Collectors.toSet()))
            .toList();
    for (int row = 0; row < rows; row++) {
      boolean mirror = true;
      boolean change = false;
      for (int check = row; check >= 0; check--) {
        if ((row + (row - check + 1)) >= rowData.size()) {
          break;
        }
        Set<Long> above = rowData.get(check);
        Set<Long> below = rowData.get(row + (row - check + 1));
        if (!above.equals(below)) {
          if (!change && smudgesPresent) {
            Set<Long> testSet;
            Set<Long> secondSet;
            if (above.size() > below.size()) {
              testSet = new HashSet<>(above);
              testSet.removeAll(below);
              secondSet = new HashSet<>(below);
              secondSet.removeAll(above);
            } else {
              testSet = new HashSet<>(below);
              testSet.removeAll(above);
              secondSet = new HashSet<>(above);
              secondSet.removeAll(below);
            }
            if (testSet.size() == 1 && secondSet.isEmpty()) {
              change = true;
            } else {
              mirror = false;
              break;
            }
          } else {
            mirror = false;
            break;
          }
        }
      }
      if (mirror && (change || !smudgesPresent)) {
        return (row + 1L) * 100L;
      }
    }
    return -1L;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day13.class);
    List<Set<Point>> puzzles = new ArrayList<>();
    Set<Point> puzzle = new HashSet<>();
    long y = 0L;
    for (String line : lines) {
      if (line.isEmpty()) {
        puzzles.add(puzzle);
        puzzle = new HashSet<>();
        y = 0L;
        continue;
      }
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          puzzle.add(new Point(x, y));
        }
      }
      y++;
    }
    puzzles.add(puzzle);
    long sum = 0L;
    for (Set<Point> puzz : puzzles) {
      sum += calculateScore(puzz, true);
    }
    return sum;
  }
}
