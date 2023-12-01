package net.ddellspe.day01;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ddellspe.utils.InputUtils;

public class Day01 {
  private Day01() {}

  static final Pattern START_PATTERN_PART1 = Pattern.compile("^\\D*(\\d).*$");
  static final Pattern END_PATTERN_PART1 = Pattern.compile("^.*(\\d)\\D*$");
  static final Pattern START_PATTERN_PART2 =
      Pattern.compile("(\\d|one|two|three|four|five|six|seven|eight|nine).*$");
  static final Pattern END_PATTERN_PART2 =
      Pattern.compile("^.*(\\d|one|two|three|four|five|six|seven|eight|nine)");
  static final Pattern DIGIT = Pattern.compile("\\d");

  static final Map<String, Integer> NUMBER_MAP =
      Map.of(
          "one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8,
          "nine", 9);

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day01.class);
    long sum = 0L;
    for (String line : lines) {
      Matcher startMatcher = START_PATTERN_PART1.matcher(line);
      Matcher endMatcher = END_PATTERN_PART1.matcher(line);
      // we can assume we'll find a match
      startMatcher.find();
      int startValue = Integer.parseInt(startMatcher.group(1));
      // we can assume we'll find a match
      endMatcher.find();
      int endValue = Integer.parseInt(endMatcher.group(1));
      sum += (startValue * 10L + endValue);
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day01.class);
    long sum = 0L;
    for (String line : lines) {
      Matcher startMatcher = START_PATTERN_PART2.matcher(line);
      Matcher endMatcher = END_PATTERN_PART2.matcher(line);
      int startValue;
      int endValue;
      // we can assume we'll find a match
      startMatcher.find();
      String match = startMatcher.group(1);
      Matcher digitMatcher = DIGIT.matcher(match);
      if (digitMatcher.find()) {
        startValue = Integer.parseInt(startMatcher.group(1));
      } else {
        startValue = NUMBER_MAP.get(match);
      }
      // we can assume we'll find a match
      endMatcher.find();
      match = endMatcher.group(1);
      digitMatcher = DIGIT.matcher(match);
      if (digitMatcher.find()) {
        endValue = Integer.parseInt(endMatcher.group(1));
      } else {
        endValue = NUMBER_MAP.get(match);
      }
      sum += (startValue * 10L + endValue);
    }
    return sum;
  }
}
