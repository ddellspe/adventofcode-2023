package net.ddellspe.day03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;
import net.ddellspe.utils.PointRange;

public class Day03 {
  private Day03() {}

  private static final Pattern NON_SYMBOLS = Pattern.compile("[^\\.0-9]");
  private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day03.class);
    Set<Point> symbols = new HashSet<>();
    Map<PointRange, Integer> numbers = new HashMap<>();
    long sum = 0;
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (NON_SYMBOLS.matcher(String.valueOf(line.charAt(x))).find()) {
          symbols.add(new Point(x, y));
        }
      }
      Matcher numberMatcher = NUMBER_PATTERN.matcher(line);
      while (numberMatcher.find()) {
        int num = Integer.parseInt(numberMatcher.group(1));
        numbers.put(
            new PointRange(
                new Point(numberMatcher.start(), y), new Point(numberMatcher.end() - 1, y)),
            num);
      }
    }
    for (Entry<PointRange, Integer> entry : numbers.entrySet()) {
      boolean found = false;
      for (Point symbol : symbols) {
        for (Point neighbor : symbol.getAllNeighbors()) {
          if (entry.getKey().inHorizontalRange(neighbor)) {
            found = true;
            sum += entry.getValue();
            break;
          }
        }
        if (found) {
          break;
        }
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day03.class);
    Set<Point> gears = new HashSet<>();
    Map<PointRange, Integer> numbers = new HashMap<>();
    long sum = 0;
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (NON_SYMBOLS.matcher(String.valueOf(line.charAt(x))).find() && line.charAt(x) == '*') {
          gears.add(new Point(x, y));
        }
      }
      Matcher numberMatcher = NUMBER_PATTERN.matcher(line);
      while (numberMatcher.find()) {
        int num = Integer.parseInt(numberMatcher.group(1));
        numbers.put(
            new PointRange(
                new Point(numberMatcher.start(), y), new Point(numberMatcher.end() - 1, y)),
            num);
      }
    }
    for (Point gear : gears) {
      int numberCount = 0;
      long product = 1L;
      for (Entry<PointRange, Integer> entry : numbers.entrySet()) {
        for (Point neighbor : gear.getAllNeighbors()) {
          if (entry.getKey().inHorizontalRange(neighbor)) {
            numberCount++;
            product *= entry.getValue();
            break;
          }
        }
      }
      if (numberCount == 2) {
        sum += product;
      }
    }
    return sum;
  }
}
