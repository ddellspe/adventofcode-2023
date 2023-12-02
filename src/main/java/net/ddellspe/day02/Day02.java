package net.ddellspe.day02;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ddellspe.utils.InputUtils;

public class Day02 {
  private Day02() {}

  private static final Pattern GREEN = Pattern.compile("(\\d+) green");
  private static final Pattern BLUE = Pattern.compile("(\\d+) blue");
  private static final Pattern RED = Pattern.compile("(\\d+) red");

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day02.class);
    long sum = 0L;
    for (String line : lines) {
      int game = Integer.parseInt(line.split(":")[0].split(" ")[1]);
      boolean valid = true;
      for (String bag : line.split(": ")[1].split("; ")) {
        long greens = 0L;
        long blues = 0L;
        long reds = 0L;
        Matcher greenMatcher = GREEN.matcher(bag);
        if (greenMatcher.find()) {
          greens += Long.parseLong(greenMatcher.group(1));
        }
        Matcher blueMatcher = BLUE.matcher(bag);
        if (blueMatcher.find()) {
          blues += Long.parseLong(blueMatcher.group(1));
        }
        Matcher redMatcher = RED.matcher(bag);
        if (redMatcher.find()) {
          reds += Long.parseLong(redMatcher.group(1));
        }
        if (reds > 12 || greens > 13 || blues > 14) {
          valid = false;
          break;
        }
      }
      if (valid) {
        sum += game;
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day02.class);
    long sum = 0L;
    for (String line : lines) {
      long greens = 0;
      long blues = 0;
      long reds = 0;
      for (String bag : line.split(": ")[1].split("; ")) {
        Matcher greenMatcher = GREEN.matcher(bag);
        if (greenMatcher.find()) {
          greens = Math.max(Long.parseLong(greenMatcher.group(1)), greens);
        }
        Matcher blueMatcher = BLUE.matcher(bag);
        if (blueMatcher.find()) {
          blues = Math.max(Long.parseLong(blueMatcher.group(1)), blues);
        }
        Matcher redMatcher = RED.matcher(bag);
        if (redMatcher.find()) {
          reds = Math.max(Long.parseLong(redMatcher.group(1)), reds);
        }
      }
      sum += (reds * greens * blues);
    }
    return sum;
  }
}
